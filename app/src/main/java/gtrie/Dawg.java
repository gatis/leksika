package gtrie;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Dawg {

    private static final int PRECISION_MASK = 0xFFFFFFFF;
    private static final int HAS_LEAF_BIT = 1 << 8;
    private static final int EXTENSION_BIT = 1 << 9;
    private static final int IS_LEAF_BIT = 1 << 31;

    private int[] units;

    public Dawg(String inputfile) throws IOException {

        try
        {
            DataInputStream dawgDataFile = new DataInputStream(new BufferedInputStream(new FileInputStream(inputfile)));
            int numberOfNodes = endianConversion(dawgDataFile.readInt());
            units = new int[numberOfNodes];

            for (int x = 0; x < numberOfNodes; x++) {
                units[x] = endianConversion(dawgDataFile.readInt());
            }
            dawgDataFile.close();
        }
        catch ( IOException iox )
        {
            iox.printStackTrace();
        }

    }

// TODO : rework as all LV unicode chars return -1
    public static int charToOffset(char character) {
        if(character >= 'a' && character <= 'z') return character - 'a';
        if(character >= 'A' && character <= 'Z') return character - 'A';
        return -1;
    }

    public Dawg(InputStream inputfile) throws IOException {

        try
        {
            DataInputStream dawgDataFile = new DataInputStream(new BufferedInputStream(inputfile));
            int numberOfNodes = endianConversion(dawgDataFile.readInt());

            //System.out.println(numberOfNodes);

            units = new int[numberOfNodes];

            for (int x = 0; x < numberOfNodes; x++) {
                units[x] = endianConversion(dawgDataFile.readInt());
            }
            dawgDataFile.close();
        }
        catch ( IOException iox )
        {
            iox.printStackTrace();
        }

    }

    private int endianConversion(int thisInteger) {
        return ((thisInteger & 0x000000ff) << 24) + ((thisInteger & 0x0000ff00) << 8) + ((thisInteger & 0x00ff0000) >>> 8) + ((thisInteger & 0xff000000) >>> 24);
    }

    // Checks whether a word is a valid word in the dictionary
    public boolean contains(byte[] key) {
        int index = followBytes(key, 0);
        if (index == -1) {
            return false;
        }
        return hasValue(index);
    }

    // Checks whether a key is a prefix of any word in the dictionary
    public boolean existPrefix(byte[] key) {
        int index = 0;
        for (int i = 0; i < key.length; i++) {
            index = followByte(key[i], index);
            if (index == -1) {
                return false;
            }
        }
        return true;
    }

    // Follow bytes and return index of last byte if exists, -1 otherwise
    public int followBytes(byte[] key, int index) {
        for (int i = 0; i < key.length; i++) {
            index = followByte(key[i], index);
            if (index == -1) {
                return -1;
            }
        }

        return index;
    }

    // Calculates next index using current index and byte value
    public int followByte(byte c, int index) {
        int o = offset(units[index]);
        int nextIndex = (index ^ o ^ (c & 0xFF)) & PRECISION_MASK;

        if (label(units[nextIndex]) != (c & 0xFF)) {
            return -1;
        }

        return nextIndex;
    }

    // Checks whether a word ends at an index
    public boolean hasValue(int index) {
        return hasLeaf(units[index]);
    }

    protected int offset(int base) {
        return ((base >> 10) << ((base & EXTENSION_BIT) >> 6)) & PRECISION_MASK;
    }

    protected int label(int base) {
        return base & (IS_LEAF_BIT | 0xFF) & PRECISION_MASK;
    }

    protected int value(int base) {
        return base & ~IS_LEAF_BIT & PRECISION_MASK;
    }

    protected boolean hasLeaf(int base) {
        return (base & HAS_LEAF_BIT & PRECISION_MASK) != 0;
    }
}
