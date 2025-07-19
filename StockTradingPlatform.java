import java.util.*;
import java.io.*;

// Represents a stock in the market
class Stock {
    String symbol;
    String companyName;
    double price;

    Stock(String symbol, String companyName, double price) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
    }
}

// Represents a stock in user's portfolio
class PortfolioItem {
    Stock stock;
    int quantity;

    PortfolioItem(Stock stock, int quantity) {
        this.stock = stock;
        this.quantity = quantity;
    }

    double getValue() {
        return stock.price * quantity;
    }
}

// Represents the user and their portfolio
class User {
    String name;
    double balance;
    ArrayList<PortfolioItem> portfolio;

    User(String name, double balance) {
        this.name = name;
        this.balance = balance;
        this.portfolio = new ArrayList<>();
    }

    void buyStock(Stock stock, int quantity) {
        double totalCost = stock.price * quantity;
        if (balance < totalCost) {
            System.out.println("❌ Not enough balance!");
            return;
        }

        balance -= totalCost;

        for (PortfolioItem item : portfolio) {
            if (item.stock.symbol.equals(stock.symbol)) {
                item.quantity += quantity;
                System.out.println("✅ Bought " + quantity + " shares of " + stock.symbol);
                return;
            }
        }

        portfolio.add(new PortfolioItem(stock, quantity));
        System.out.println("✅ Bought " + quantity + " shares of " + stock.symbol);
    }

    void sellStock(Stock stock, int quantity) {
        for (PortfolioItem item : portfolio) {
            if (item.stock.symbol.equals(stock.symbol)) {
                if (item.quantity < quantity) {
                    System.out.println("❌ Not enough shares to sell.");
                    return;
                }

                item.quantity -= quantity;
                balance += stock.price * quantity;

                System.out.println("✅ Sold " + quantity + " shares of " + stock.symbol);

                if (item.quantity == 0) {
                    portfolio.remove(item);
                }
                return;
            }
        }
        System.out.println("❌ You don't own this stock.");
    }

    void showPortfolio() {
        System.out.println("\n📊 " + name + "'s Portfolio:");
        double totalValue = 0;
        for (PortfolioItem item : portfolio) {
            double value = item.getValue();
            totalValue += value;
            System.out.printf("Stock: %s | Qty: %d | Price: %.2f | Value: %.2f\n",
                    item.stock.symbol, item.quantity, item.stock.price, value);
        }
        System.out.printf("💰 Balance: ₹%.2f\n", balance);
        System.out.printf("📈 Total Portfolio Value: ₹%.2f\n", totalValue + balance);
    }

    void savePortfolioToFile() {
        try (FileWriter writer = new FileWriter("Portfolio_" + name + ".txt")) {
            writer.write(name + "'s Portfolio\n");
            for (PortfolioItem item : portfolio) {
                writer.write("Stock: " + item.stock.symbol + ", Qty: " + item.quantity +
                        ", Price: " + item.stock.price + "\n");
            }
            writer.write("Balance: ₹" + balance + "\n");
            System.out.println("📁 Portfolio saved to file.");
        } catch (IOException e) {
            System.out.println("❌ Failed to save file.");
        }
    }
}

public class StockTradingPlatform {

    static ArrayList<Stock> market = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Sample market stocks
        market.add(new Stock("TCS", "Tata Consultancy Services", 3500));
        market.add(new Stock("RELI", "Reliance Industries", 2400));
        market.add(new Stock("INFY", "Infosys Ltd.", 1600));
        market.add(new Stock("WIPR", "Wipro Ltd.", 580));
        market.add(new Stock("HDFC", "HDFC Bank", 1500));

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        User user = new User(name, 10000); // ₹10,000 starting balance

        boolean running = true;

        while (running) {
            System.out.println("\n===== STOCK TRADING MENU =====");
            System.out.println("1. View Market");
            System.out.println("2. Buy Stock");
            System.out.println("3. Sell Stock");
            System.out.println("4. View Portfolio");
            System.out.println("5. Save Portfolio to File");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear buffer

            switch (choice) {
                case 1:
                    displayMarket();
                    break;
                case 2:
                    buyStock(scanner, user);
                    break;
                case 3:
                    sellStock(scanner, user);
                    break;
                case 4:
                    user.showPortfolio();
                    break;
                case 5:
                    user.savePortfolioToFile();
                    break;
                case 6:
                    running = false;
                    System.out.println("👋 Thank you for using the Stock Trading Platform!");
                    break;
                default:
                    System.out.println("❌ Invalid choice. Try again.");
            }
        }

        scanner.close();
    }

    static void displayMarket() {
        System.out.println("\n📈 Available Stocks:");
        for (Stock s : market) {
            System.out.printf("%s (%s) - ₹%.2f\n", s.companyName, s.symbol, s.price);
        }
    }

    static Stock getStockBySymbol(String symbol) {
        for (Stock s : market) {
            if (s.symbol.equalsIgnoreCase(symbol)) {
                return s;
            }
        }
        return null;
    }

    static void buyStock(Scanner scanner, User user) {
        System.out.print("Enter stock symbol to buy: ");
        String symbol = scanner.nextLine();
        Stock stock = getStockBySymbol(symbol);
        if (stock == null) {
            System.out.println("❌ Stock not found.");
            return;
        }
        System.out.print("Enter quantity to buy: ");
        int qty = scanner.nextInt();
        scanner.nextLine();
        user.buyStock(stock, qty);
    }

    static void sellStock(Scanner scanner, User user) {
        System.out.print("Enter stock symbol to sell: ");
        String symbol = scanner.nextLine();
        Stock stock = getStockBySymbol(symbol);
        if (stock == null) {
            System.out.println("❌ Stock not found.");
            return;
        }
        System.out.print("Enter quantity to sell: ");
        int qty = scanner.nextInt();
        scanner.nextLine();
        user.sellStock(stock, qty);
    }
}
