package csc454Homework4;

public class VendingMachine
{
    private int q, n, d, v;
    private boolean c;
    private double timeElapsed;
    private double alarm;

    public VendingMachine(int q, int n, int d)
    {
        this.q = q;
        this.n = n;
        this.d = d;
        this.v = 0;
        this.c = false;
        this.timeElapsed = 0.0;
        this.alarm = Double.POSITIVE_INFINITY;
    }

    public String insertCoin(String coin, double currentTime)
    {
        if (currentTime < timeElapsed)
        {
            return "Invalid input. You cannot travel backward in time.\n";
        }

        timeElapsed = currentTime;

        String output = "Output: nothing\n";

        if (currentTime >= alarm)
        {
            if (currentTime == alarm)
            {
                output = "Output: " + lambda();
                deltaCon(coin);

                if (timeElapsed == alarm)
                {
                    output += lambda();
                    deltaInt();
                }
            }
            else
            {
                output = "Output: " + lambda();
                deltaInt();
                deltaExt(coin, currentTime);
            }
        }
        else
        {
            deltaExt(coin, currentTime);
        }

        return output;
    }

    private double ta()
    {
        if (c) return 0;
        if (v > 0) return 2.0;
        return Double.POSITIVE_INFINITY;
    }

    private String lambda()
    {
        StringBuilder response = new StringBuilder();

        if (c) response.append("c ");

        int coffeeCount = v / 100;
        int change = v % 100;

        response.append("coffee ".repeat(Math.max(0, coffeeCount)));

        while (change > 0)
        {
            if (change >= 25 && q > 0)
            {
                response.append("q ");
                change -= 25;
            }
            else if (change >= 10 && d > 0)
            {
                response.append("d ");
                change -= 10;
            }
            else if (change >= 5 && n > 0)
            {
                response.append("n ");
                change -= 5;
            }
        }

        if (response.length() == 0)
        {
            response.append("nothing");
        }

        response.append("\n");

        return response.toString();
    }

    private void deltaInt()
    {
        int change = v % 100;
        int[] changeCoins = getChange(change);
        q -= changeCoins[0];
        d -= changeCoins[1];
        n -= changeCoins[2];
        v = 0;
        c = false;

        alarm += ta();
    }

    private void deltaExt(String x, double e)
    {
        switch (x)
        {
            case "q":
                q++;
                v += 25;
                break;
            case "d":
                d++;
                v += 10;
                break;
            case "n":
                n++;
                v += 5;
                break;
            case "c":
                c = true;
                alarm += ta();
                return;
        }

        alarm = e + ta();
    }

    private void deltaCon(String x)
    {
        if (c)
        {
            c = false;
            alarm += ta();
            return;
        }

        int change = v % 100;
        int[] changeCoins = getChange(change);

        switch (x)
        {
            case "q":
                q = q + 1 - changeCoins[0];
                d -= changeCoins[1];
                n -= changeCoins[2];
                v += 25;  // Modify here
                break;
            case "d":
                q -= changeCoins[0];
                d = d + 1 - changeCoins[1];
                n -= changeCoins[2];
                v += 10;  // Modify here
                break;
            case "n":
                q -= changeCoins[0];
                d -= changeCoins[1];
                n = n + 1 - changeCoins[2];
                v += 5;   // Modify here
                break;
            case "c":
                c = true;
                break;
        }

        alarm += ta();
    }

    private int[] getChange(int change)
    {
        int quarters = 0, dimes = 0, nickels = 0;

        while (change >= 25 && q > 0)
        {
            quarters++;
            change -= 25;
        }

        while (change >= 10 && d > 0)
        {
            dimes++;
            change -= 10;
        }

        while (change > 0 && n > 0)
        {
            nickels++;
            change -= 5;
        }

        return new int[]{quarters, dimes, nickels};
    }
}
