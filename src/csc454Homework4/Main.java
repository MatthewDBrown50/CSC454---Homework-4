package csc454Homework4;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        VendingMachine machine = new VendingMachine(10, 10, 10);
        Scanner scanner = new Scanner(System.in);

        while (true)
        {
            System.out.print("Input: ");
            String[] input = scanner.nextLine().split(" ");
            System.out.println();

            String coin;
            double currentTime;

            try
            {
                coin = input[0];
                currentTime = Double.parseDouble(input[1]);

                if (!coin.equals("q") && !coin.equals("d") && !coin.equals("n") && !coin.equals("c"))
                {
                    System.out.println("Invalid input. Please enter a valid coin and time, separated by a space (ex: \"q 1.5\".\n");
                    continue;
                }
            }
            catch (Exception e)
            {
                System.out.println("Invalid input. Please enter a valid coin and time, separated by a space (ex: \"q 1.5\").\n");
                continue;
            }

            String output = machine.insertCoin(coin, currentTime);
            System.out.println(output);
        }
    }
}