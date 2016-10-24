import java.math.BigInteger;
import java.security.*;

class Keys {
    BigInteger n = new BigInteger("951386374109");
    BigInteger d = new BigInteger("279263220413");
    BigInteger e = new BigInteger("17");
}

public class DigitalSignature {
    String byteArrayToHexStr(byte[] data) {
        String output = "";
        String tempStr = "";
        int tempInt = 0;
        for (int cnt = 0; cnt < data.length; cnt++) {
            tempInt = data[cnt] & 0xFF;
            tempStr = Integer.toHexString(tempInt);
            if (tempStr.length() == 1)
                tempStr = "0" + tempStr;
            output = output + tempStr;
        }
        return output.toUpperCase();
    }

    String encode(String msg) {
        byte[] textChars = msg.getBytes();
        String temp = "";
        String encodedMsg = "";
        for (int cnt = 0; cnt < msg.length(); cnt++) {
            temp = String.valueOf(textChars[cnt] - ' ');
            if (temp.length() < 2) temp = "0" + temp;
            encodedMsg += temp;
        }
        return encodedMsg;
    }

    String decode(String encodedMsg) {
        String temp = "";
        String decodedText = "";
        for (int cnt = 0; cnt < encodedMsg.length(); cnt += 2) {
            temp = encodedMsg.substring(cnt, cnt + 2);
            int val = Integer.parseInt(temp) + 32;
            decodedText += String.valueOf((char) val);
        }
        return decodedText;
    }

    String doRSA(String inputString, BigInteger exp, BigInteger n, int blockSize) {
        BigInteger block;
        BigInteger output;
        String temp = "";
        String outputString = "";
        for (int cnt = 0; cnt < inputString.length(); cnt += blockSize) {
            temp = inputString.substring(cnt, cnt + blockSize);
            block = new BigInteger(temp);
            output = block.modPow(exp, n);
            temp = output.toString();
            while (temp.length() < blockSize)
                temp = "0" + temp;
            outputString += temp;
        }
        return outputString;
    }

    byte[] digestIt(byte[] dataIn) {
        byte[] theDigest = null;
        try {
            MessageDigest msgDigest = MessageDigest.getInstance("SHA", "SUN");
            msgDigest.update(dataIn);
            theDigest = msgDigest.digest();
        } catch (Exception e) {
            System.out.println(e);
        }
        return theDigest;
    }

    public static void main(String[] args) {
        Keys aliceKeys = new Keys();
        String aliceMsg = "We are C-Batch People in SS Lab implementing Digital Signature Experiment\nfriend, Alice.";
        int blockSize = 12;
        DigitalSignature obj = new DigitalSignature();

        System.out.println("1. Alice's keys:");
        System.out.println("2. e: " + aliceKeys.e);
        System.out.println("3. d: " + aliceKeys.d);
        System.out.println("4. n: " + aliceKeys.n);
        System.out.println("5. Block size: " + blockSize);
        System.out.println("6. Alice's msg: " + aliceMsg);

        byte[] aliceDigest = obj.digestIt(aliceMsg.getBytes());
        System.out.println("7. Alice's msg digest: " + obj.byteArrayToHexStr(aliceDigest));

        int tailLen = aliceDigest.length % blockSize;
        int extendLen = 0;
        if ((tailLen > 0))
            extendLen = blockSize - tailLen;
        byte[] aliceExtendedDigest = new byte[aliceDigest.length + extendLen];
        System.arraycopy(aliceDigest, 0, aliceExtendedDigest, 0, aliceDigest.length);

        String aliceExtendedDigestAsHex = obj.byteArrayToHexStr(aliceExtendedDigest);
        System.out.println("8. Alice's extended msg digest: " + aliceExtendedDigestAsHex);

        String aliceEncodedDigest = obj.encode(aliceExtendedDigestAsHex);
        String aliceSignature = obj.doRSA(aliceEncodedDigest, aliceKeys.d, aliceKeys.n, blockSize);
        System.out.println("9. Alice's encrypted digital signature: " + aliceSignature);

        String aliceSignedMsg = aliceMsg + aliceSignature;
        System.out.println("10. Alice's signed msg: " + aliceSignedMsg);
        String aliceMsgLenAsStr = "" + aliceMsg.length();

        if ((aliceMsgLenAsStr.length() > 4) || (aliceMsgLenAsStr.length() <= 0)) {
            System.out.println("Message length error.");
            System.exit(0);
        }
        if (aliceMsgLenAsStr.length() == 1)
            aliceMsgLenAsStr = "000" + aliceMsgLenAsStr;
        else if (aliceMsgLenAsStr.length() == 2)
            aliceMsgLenAsStr = "00" + aliceMsgLenAsStr;
        else if (aliceMsgLenAsStr.length() == 3)
            aliceMsgLenAsStr = "0" + aliceMsgLenAsStr;
        else if (aliceMsgLenAsStr.length() == 1)
            aliceMsgLenAsStr = "000" + aliceMsgLenAsStr;

        aliceSignedMsg = aliceSignedMsg + aliceMsgLenAsStr;
        System.out.println("11. Alice's signed msg with msg length " + "appended: " + aliceSignedMsg);

        String bobSignedMsg = aliceSignedMsg;
        int bobMsgLen = Integer.parseInt(bobSignedMsg.substring(bobSignedMsg.length() - 4));

        System.out.println("12. Bob's calculated msg len: " + bobMsgLen);
        String bobMsgText = bobSignedMsg.substring(0, bobMsgLen);
        System.out.println("13. Bob's extracted msg text: " + bobMsgText);
        String bobExtractedSignature = bobSignedMsg.substring(bobMsgLen, bobSignedMsg.length() - 4);
        System.out.println("14. Bob's extracted extended digital " + "signature: " + bobExtractedSignature);
        String bobDecryptedExtendedSignature = obj.doRSA(bobExtractedSignature, aliceKeys.e, aliceKeys.n, blockSize);
        String bobDecodedExtendedSignature = obj.decode(bobDecryptedExtendedSignature);
        System.out.println("15. Bob's decoded extended digital " + "signature: " + bobDecodedExtendedSignature);
        String bobDecodedSignature = bobDecodedExtendedSignature.substring(0, 40);
        System.out.println("16. Bob's decoded digital signature: " + bobDecodedSignature);
        byte[] bobDigest = obj.digestIt(bobMsgText.getBytes());
        System.out.println("17. Bob's digest: " + obj.byteArrayToHexStr(bobDigest));

        if (bobDecodedSignature.equals(obj.byteArrayToHexStr(bobDigest)))
            System.out.println("18. Bob's conclusion: " + "Valid signature");
        else
            System.out.println("18. Bob's conclusion: " + "Invalid signature");
    }
}
