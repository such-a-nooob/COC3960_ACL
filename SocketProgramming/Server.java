import java.io.*;
import java.net.*;
import java.util.*;

class Server {
    public static void main(String args[]) throws Exception {
        System.out.println("Waiting for client! ");
        ServerSocket servSocket = new ServerSocket(1111);
        Socket s = servSocket.accept();
        System.out.println("Client connected! ");
        DataInputStream din = new DataInputStream(s.getInputStream());
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        int n = din.readInt();
        int a[] = new int[n];
        System.out.print("Data recieved : ");
        for (int i = 0; i < n; i++) {
            a[i] = din.readInt();
            System.out.print(a[i] + " ");
        }
        for (int i = 0; i < n - 1; i++)
            for (int j = 0; j < n - i - 1; j++)
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }

        System.out.print("\nData sent : ");
        for (int i = 0; i < n; i++) {
            dout.writeInt(a[i]);
            System.out.print(a[i] + " ");
        }

        s.close();
        servSocket.close();
        System.out.println("\nClient disconnected! ");
    }
}