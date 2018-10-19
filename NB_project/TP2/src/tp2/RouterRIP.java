/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp2;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.net.DatagramPacket;

/**
 *
 * @author renatojuniortmp
 */
public class RouterRIP {

    private DatagramSocket socket;  // Socket UDP
    private String ip;              // IP do roteador
    private int period;             // Período de atualização

    private static final int PORTA_ROTEADOR = 55151;

    public RouterRIP(String ip, int period) {
        this.ip = ip;
        this.period = period;
        try {
            this.socket = new DatagramSocket(PORTA_ROTEADOR, InetAddress.getByName(ip));
        } catch (UnknownHostException | SocketException ex) {
            System.out.println("Erro ao criar o socket! " + ex.getLocalizedMessage());
            System.exit(0);
        }
    }
    
    void send_data_message(DataMessage m,String ip_to_send,String port){
        try{            
            DatagramPacket p = new DatagramPacket(m.getMessageJson().getBytes(),m.getMessageJson().getBytes().length);
            p.setAddress(InetAddress.getByName(ip_to_send));
            p.setPort(Integer.parseInt(port));
            socket.send(p);

        } catch (UnknownHostException | SocketException ex) {
            System.out.println("Erro ao criar o socket! " + ex.getLocalizedMessage());
            System.exit(0);
        } catch (Exception e2){
            System.out.println("É so isso.. não tem mais jeito... acabou!");
        }
    }
}
