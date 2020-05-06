import java.util.ArrayList;
import java.util.Date;
import java.lang.String;
public class Block {

    public int index;
    public String data;
    public long timeStamp;
    public String hash;
    public String previousHash;
    public ArrayList<Transaction> transactions = new ArrayList<Transaction>(); //our data will be a simple message.
//    public Transaction[] transaction;
    public int difficulty;
    public int nonce=0;

    public static int BLOCK_GENERATION_INTERVAL = 1;
    public static int DIFFICULTY_ADJUSTMENT_INTERVAL = 1;

//    public static String calculateHash(int index, String previousHash, long timeStamp, String data) {
//        return StringUtil.applySha256(
//                index + previousHash +
//                        timeStamp +
//                        data
//        );
//    }
    public Block(String previousHash ) {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();

        this.hash = calculateHash(); //Making sure we do this after we set the other values.
    }

    public String calculateHash() {
        String calculatedhash= StringUtil.applySha256(
                    previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce)+
                        data
        );
        return calculatedhash;
    }

    //Increases nonce value until hash target is reached.
    public void mineBlock(int difficulty) {
        data = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(difficulty); //Create a string with difficulty * "0"
        while(!hash.substring( 0, difficulty).equals(target)) {
            nonce ++;
            hash = calculateHash();
        }
        System.out.println("Block Mined!!! : " + hash);
    }
    
//    public static String calculateHash(int index, String previousHash, long timeStamp, String data, int difficulty, int nonce){
//        return StringUtil.applySha256(
//                index + previousHash +timeStamp + data + difficulty + nonce
//        );
//
//    }

    public Block(int index, String hash, String previousHash, long timeStamp,String data) {
        this.index = index;
        this.hash = hash;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.data = data;

    }

    public Block(int index, String previousHash,long timeStamp, String data){
        this.index = index;
        this.hash = calculateHash();
//
    }

    public Block(int index, String hash){
        this.index = index;
        this.hash = hash;
    }

    public Block(int index, String hash, String previousHash, long timeStamp, String data, int difficulty, int nonce){
        this.index = index;
//        this.hash = hash;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.data = data;
        this.difficulty = difficulty;
        this.nonce = nonce;
        this.hash=calculateHash();
    }

    public Block(int index, String previousHash, long timeStamp, String data, int difficulty, int nonce){
        this.index = index;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.data = data;
        this.difficulty = difficulty;
        this.nonce = nonce;
    }

//    private String hash(Block newBlock){
//        int index = newBlock.index;
//        String previousHash = newBlock.previousHash;
//        Long timeStamp = newBlock.timeStamp;
//        String data = newBlock.data;
//        String hash;
//        hash = calculateHash(index, previousHash, timeStamp, data);
//        return hash;
//    }

    public String hexToBinary(String hash){
        this.hash = hash;
        return hash;
    }

    /**创建第一个块函数**/
//    public static Block addFirstBlock(){
//        int index = 0;
//        String data = "this is the first Block";
//        long timeStamp = new Date().getTime();
//        String previousHash = null;
//        String hash = calculateHash(index, previousHash, timeStamp, data);
//        return new Block(index, hash, timeStamp, data);
//    }
    /** 获得前一个块的信息函数**/
//    public static Block getLatestBlock(){
//        Block previousBlock = getLatestBlock();
//        int index = previousBlock.index;
//        if(previousBlock.index==0){
//            String hash = addFirstBlock().hash;
//            return new Block(index, hash);
//        }
//        index++;
//
//        Block block = new Block(index, previousBlock.hash);
//        return block;
//    }
    /** 区块链创建函数**/
//    public Block generateNextBlock(String data){
//        Block previousBlock = getLatestBlock();
//        int nextIndex = previousBlock.index + 1;
//        String blockData = data;
//        long nextTimestamp = new Date().getTime();
//        String nextHash = calculateHash(nextIndex, previousBlock.hash, nextTimestamp, blockData);
//        Block newBlock = new Block(nextIndex, nextHash, previousHash,nextTimestamp, blockData);
//        return newBlock;
//    }
    /** 判断区块链有效性函数**/
//    public Boolean isValidNewBlock(Block newBlock, Block previousBlock){
//        if(previousBlock.index + 1 != newBlock.index){
//            return false;
//        }
//        else if(previousBlock.hash != newBlock.previousHash){
//            return false;
//        }
//        else if(hash(newBlock)!=newBlock.hash){
//            return false;
//        }
//        else return true;
//    }

    private static String repeat(String str, int difficulty){
        final StringBuilder buf = new StringBuilder();
        for (int i = 0; i<difficulty; i++){
            buf.append(str);
        }
        return buf.toString();
    }
    /** 增添difficulty值判断hash值前面有几个0**/
    public Boolean hashMatchesDifficulty(String hash, int difficulty){
        String hashInBinary = hexToBinary(hash);
        String requiredPrefix = repeat("0",difficulty);
        return hashInBinary.startsWith(requiredPrefix);

    }

    /** 寻找符合要求的即满足difficulty的区块hash值 **/
//    public Block findBlock(int index, String previousHash, int timeStamp, String data, int difficulty){
//        int nonce = 0;
//        while (true){
//            String hash = calculateHash(index, previousHash, timeStamp, data, difficulty, nonce);
//            if (hashMatchesDifficulty(hash, difficulty)){
//                return new Block(index, previousHash, timeStamp, data, difficulty, nonce);
//            }
//            nonce++;
//        }
//    }
//    /** 寻找符合要求的即满足difficulty的区块hash值 **/
//    public Block findBlock(int difficulty){
//        int nonce = 0;
//        while (true){
//            String hash = calculateHash(index, previousHash, timeStamp, data, difficulty, nonce);
//            if (hashMatchesDifficulty(hash, difficulty)){
//                return new Block(index, previousHash, timeStamp, data, difficulty, nonce);
//            }
//            nonce++;
//        }
//    }

    /**判断difficulty函数，若每10个块生成时间大于两倍预计时间，difficulty-1； 若每10个块生成时间小于二分之一预计时间，则difficulty+1 **/
    public int getAdjustedDifficulty(Block lastsBlock, Block[] aBlockchain){
        Block prevAdjustmentBlock = aBlockchain[aBlockchain.length - DIFFICULTY_ADJUSTMENT_INTERVAL];
        long timeExpected = BLOCK_GENERATION_INTERVAL * DIFFICULTY_ADJUSTMENT_INTERVAL;
        long timeTaken = lastsBlock.timeStamp - prevAdjustmentBlock.timeStamp;
        if (timeTaken < timeExpected / 2){
            return prevAdjustmentBlock.difficulty + 1;
        }else if (timeTaken > timeExpected * 2){
            return prevAdjustmentBlock.difficulty - 1;
        }else{
            return prevAdjustmentBlock.difficulty;
        }
    }
    /** 取得difficulty函数 **/
    public int getDifficulty(Block[] aBlockchain){
        Block latestBlock = aBlockchain[aBlockchain.length-1];
        if (latestBlock.index % DIFFICULTY_ADJUSTMENT_INTERVAL == 0 && latestBlock.index != 0){
            return getAdjustedDifficulty(latestBlock, aBlockchain);
        }else {
            return latestBlock.difficulty;
        }
    }

    //Add transactions to this block
    public boolean addTransaction(Transaction transaction) {
        //process transaction and check if valid, unless block is genesis block then ignore.
        if(transaction == null) return false;
        if((previousHash != "0")) {
            if((transaction.processTransaction() != true)) {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }

}

