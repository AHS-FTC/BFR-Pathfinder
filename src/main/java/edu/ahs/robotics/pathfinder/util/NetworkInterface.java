package edu.ahs.robotics.pathfinder.util;

import edu.ahs.robotics.pathfinder.ui.windows.NoConnectionGraphic;
import javafx.scene.image.Image;

import java.io.*;
import java.net.*;

/**
 * Util class for interacting with adb and the phones
 *
 * Most of this class graciously stol'd from https://stackoverflow.com/questions/20922600/execute-adb-command-from-java-program
 * thx Sarpe
 */
public class NetworkInterface {
    private static final String[] WIN_RUNTIME = { "cmd.exe", "/C" };
    private static final String[] OS_LINUX_RUNTIME = { "/bin/bash", "-l", "-c" };

    private static final String IP = "192.168.49";
    private static final int PORT = 5555;

    private static DataInputStream tcpInputStream;

    private static final NoConnectionGraphic NO_CONNECTION_GRAPHIC = new NoConnectionGraphic();


    static {


    }


    private NetworkInterface() {} //don't make an instance
//
//    private static <T> T[] concat(T[] first, T[] second) {
//        T[] result = Arrays.copyOf(first, first.length + second.length);
//        System.arraycopy(second, 0, result, first.length, second.length);
//        return result;
//    }
//
//    public static List<String> runProcess(boolean isWin, String... command) {//todo figure out a more elegant solution than isWin bool
//        System.out.print("command to run: ");
//        for (String s : command) {
//            System.out.print(s);
//        }
//        System.out.print("\n");
//        String[] allCommand = null;
//        try {
//            if (isWin) {
//                allCommand = concat(WIN_RUNTIME, command);
//            } else {
//                allCommand = concat(OS_LINUX_RUNTIME, command);
//            }
//            ProcessBuilder pb = new ProcessBuilder(allCommand);
//            pb.redirectErrorStream(true);
//            Process p = pb.start();
//            //p.waitFor();
//            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String _temp = null;
//            List<String> line = new ArrayList<>();
//            while ((_temp = in.readLine()) != null) {
//                System.out.println("temp line: " + _temp);
//                line.add(_temp);
//            }
//            System.out.println("result after command: " + line);
//            return line;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public static void init(){
        try {
            ServerSocket serverSocket = new ServerSocket(6969);
            Socket s = serverSocket.accept();
            System.out.println("setup");
            tcpInputStream = new DataInputStream(s.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Image receiveTCP(){
//        try {
//            byte[] data = new byte[tcpInputStream.readInt()];
//            tcpInputStream.readFully(data);
//
//            return new Image(new ByteArrayInputStream(data));
//        } catch (IOException e) {
           return NO_CONNECTION_GRAPHIC.getImage();
        //}
    }

    public static void receiveUDP(){ //todo put in a thread
        try(DatagramSocket clientSocket = new DatagramSocket(PORT)) {
            while (true){
                byte[] buffer = new byte[65507];
                DatagramPacket packet = new DatagramPacket(buffer, 0, buffer.length);
                clientSocket.receive(packet);

                String message = new String(packet.getData());
                System.out.println(message);
                //break;
            }
        }catch (SocketException e){
            e.printStackTrace();
        }catch (IOException e){

        }

    }

}
