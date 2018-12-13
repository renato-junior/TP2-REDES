package tp2;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 *
 * @author renato
 */
public class UpdateRoutesThread extends Thread {

    private RouterRIP router;
    private long updatePeriod;

    public UpdateRoutesThread(RouterRIP router) {
        this.router = router;
        this.updatePeriod = router.getPeriod() * 1000; // Transforma o período em ms
    }

    @Override
    public void run() {
        long startTimeUpdate = System.currentTimeMillis();
        while (true) {
            try {
                long currentTime = System.currentTimeMillis();
                if (currentTime - startTimeUpdate >= updatePeriod) {
                    this.sendUpdateToNeighbours();
                    startTimeUpdate = currentTime;
                }
                this.removeOldRoutes();
            } catch (NullPointerException | ConcurrentModificationException e) {
            }
        }
    }

    /**
     * Remove as rotas que foram inseridas há mais que updatePeriod.
     */
    private synchronized void removeOldRoutes() {
        long currentTime = System.currentTimeMillis();
        long removePeriod = updatePeriod * 4;
        List<RoutingTableEntry> routes = router.getKnownRoutes();
        List<RoutingTableEntry> expiredRoutes = new ArrayList<>();
        for (RoutingTableEntry r : routes) {
            if (!verifyNeighbour(r) && currentTime - r.getAddTime() > removePeriod) {
                expiredRoutes.add(r);
            }
        }
        routes.removeAll(expiredRoutes);
    }

    /**
     * Envia a mensagem de atualização com todas as rotas conhecidas para os
     * vizinhos.
     */
    private synchronized void sendUpdateToNeighbours() {
        for (RoutingTableEntry r : router.getKnownRoutes()) {
            if (verifyNeighbour(r)) {
                sendUpdateToNeighbour(r.getIpDestination());
            }
        }
    }

    /**
     * Envia uma mensagem de atualização para o vizinho com todas as rotas
     * conhecidas.
     *
     * @param neighbourIp o vizinho para o qual será enviada a mensagem de
     * atualização.
     */
    private synchronized void sendUpdateToNeighbour(String neighbourIp) {
        UpdateMessage updateMessage = new UpdateMessage(router.getIp(), neighbourIp);
        for (RoutingTableEntry r : router.getKnownRoutes()) {
            if (!r.getNextHop().equals(neighbourIp) && !r.getIpDestination().equals(neighbourIp)) {
                updateMessage.addDistance(r.getIpDestination(), r.getDistance());
            }
        }
        router.sendMessage(updateMessage, neighbourIp);
    }

    /**
     * Verifica se o roteador é vizinho de router. Roteador será vizinho se o ip
     * de destino e o next hop forem iguais na entrada da tabela de roteamento.
     *
     * @param r o possível vizinho.
     * @return true, se for vizinho, e falso, se não for.
     */
    private synchronized boolean verifyNeighbour(RoutingTableEntry r) {
        return r.getIpDestination().equals(r.getNextHop());
    }

}
