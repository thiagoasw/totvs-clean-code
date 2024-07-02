package handson;

import static java.util.stream.Collectors.joining;

import java.util.ArrayList;
import java.util.List;

public class RentalStatement {

    private final String customerName;
    private final List<Rental> rentals = new ArrayList<Rental>();

    private double totalAmount;
    private int frequentRenterPoints;

    public RentalStatement(String customerName) {
        this.customerName = customerName;
    }

    public void addRental(Rental rental) {
        rentals.add(rental);
    }

    public String makeRentalStatement() {
        clearTotals();
        return makeHeader() + makeRentalLines() + makeSummary();
    }

    private void clearTotals() {
        totalAmount = 0;
        frequentRenterPoints = 0;
    }

    private String makeHeader() {
        return "Rental Record for " + getCustomerName() + "\n";
    }

    //    Alternative style
    //    private String makeRentalLines() {
    //      String rentalLines = "";
    //        for (Rental rental : rentals)
    //            rentalLines += makeRentalLine(rental);
    //        return rentalLines;
    //    }

    private String makeRentalLines() {
        return rentals.stream()
            .map(this::makeRentalLine)
            .collect(joining());
    }

    private String makeRentalLine(Rental rental) {
        double thisAmount = rental.determineAmount();
        frequentRenterPoints += rental.determineFrequentRenterPoints();
        totalAmount += thisAmount;
        return formatRentalLine(rental, thisAmount);
    }

    private String formatRentalLine(Rental rental, double thisAmount) {
        return "\t" + rental.getTitle() + "\t" + thisAmount + "\n";
    }

    private String makeSummary() {
        return "You owed %s\nYou earned %s frequent renter points\n"
            .formatted(totalAmount, frequentRenterPoints);
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmountOwed() {
        return totalAmount;
    }

    public int getFrequentRenterPoints() {
        return frequentRenterPoints;
    }
}