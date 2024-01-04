package business;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/* Immutable */
final public class CheckoutRecordEntry implements Serializable {
    private final Calendar dueDate;
    private final Calendar checkoutDate;
    private final BookCopy bookCopy;

    public CheckoutRecordEntry(Calendar dueDate, Calendar checkoutDate, BookCopy bookCopy) {
        this.dueDate = dueDate;
        this.bookCopy = bookCopy;
        this.checkoutDate = checkoutDate;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    public Calendar getCheckoutDate() {
        return checkoutDate;
    }

    public BookCopy getBookCopy() {
        return bookCopy;
    }

    @Override
    public String toString() {
        String pattern = "MM/dd/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return "{" + "dueDate = " + simpleDateFormat.format((dueDate.getTime())) +
                ", checkoutDate = " + simpleDateFormat.format((checkoutDate.getTime())) + "}";
    }

    private static final long serialVersionUID = -2226197306790745013L;
}
