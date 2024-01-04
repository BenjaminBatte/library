package business;

import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
    private final String memberId;
    private final CheckoutRecord checkoutRecord;

    public LibraryMember(String memberId, String fName, String lName, String tel, Address add) {
        super(fName, lName, tel, add);
        this.memberId = memberId;
        checkoutRecord = new CheckoutRecord();
    }

    public String getMemberId() {
        return memberId;
    }

    @Override
    public String toString() {
        return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() +
                ", " + getTelephone() + " " + getAddress();
    }

    public CheckoutRecord getCheckoutRecord() {
        return checkoutRecord;
    }

    private static final long serialVersionUID = -2226197306790714013L;
}
