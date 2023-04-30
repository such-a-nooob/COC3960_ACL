import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 1111);
        System.out.print("Enter the size of array: ");
        Scanner sc = new Scanner(System.in);
        //int r;
        int n = sc.nextInt();
        int a[] = new int[n];
        System.out.println("Enter elements of array:");
        DataOutputStream dout = new DataOutputStream(s.getOutputStream());
        dout.writeInt(n);
        for (int i = 0; i < n; i++) {
            int r = sc.nextInt();
            dout.writeInt(r);
        }
        DataInputStream din = new DataInputStream(s.getInputStream());
        System.out.println("Sorted Array:");
        for (int i = 0; i < n; i++) {
            int r = din.readInt();
            System.out.print(r + " ");
        }
        s.close();
    }
}