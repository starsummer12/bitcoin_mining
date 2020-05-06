
import java.security.PublicKey;

public class TransactionOutput {
    public String id;
    public PublicKey reciepient; //also known as the new owner of these coins.
    public float value; //the amount of coins they own
    public String TransactionId; //the id of the transaction this output was created in
//    public String address;
//    public double number;

//    public TransactionOutput(String address, double number,PublicKey reciepient) {
//        this.reciepient = reciepient;
//        this.address = address;
//        this.number = number;
//    }

    //Constructor
    public TransactionOutput(PublicKey reciepient, float value, String parentTransactionId) {
        this.reciepient = reciepient;
        this.value = value;
        this.TransactionId = TransactionId;
        this.id = StringUtil.applySha256(StringUtil.getStringFromKey(reciepient)+Float.toString(value)+parentTransactionId);
    }

    //Check if coin belongs to you
    public boolean isMine(PublicKey publicKey) {
        return (publicKey == reciepient);
    }
}
