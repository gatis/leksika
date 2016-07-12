package gtrie;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class BuildTrie {

	public BuildTrie(InputStream dictfile) throws IOException {
		//d = makeTrie(dictfile);
		da = new Dawg(dictfile);
	}

	private static Dawg da;

	private String[] board;

	private static String[] splitEqually(String text) {
		Integer size = (int)Math.sqrt(text.length());
		String[] ret = new String[size];
		int i=0;
		for (int start = 0; start < text.length(); start +=size) {
			ret[i] = text.substring(start, Math.min(text.length(),  start+size));
			i++;
		}
		
		return ret;
	}

	
	private static Integer pathToMask(ArrayList<Integer> path){

		int uMask = -1;
		
		//System.out.println(uMask);
		
		for (int n=0; n<path.size(); n++ ) {
			uMask = uMask & ~(1<<path.get(n));
		}		
		
		return ~uMask;
	}

	public LinkedHashMap<String, Solution> SolveBoggle(String b){

        board = splitEqually(b);
        BS = new LinkedHashMap<String, Solution>();

        for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board.length; x++) {
				String c = String.valueOf(board[y].charAt(x));
				//System.out.println(xyToPos(y,x,5));
				ArrayList<Integer> chkdfields = new ArrayList<>();
				chkdfields.add((xyToPos(y,x,board.length)));
				SolveBoggle(x, y, c, chkdfields);
			}
		}

		return BS;
	}

	private LinkedHashMap<String, Solution> BS;

	private void SolveBoggle(int x, int y, String stem, ArrayList<Integer> cf){

		int rows = board.length;
		int cols = board[0].length();

		String st;
		st = stem;

		if (da.contains(stem.getBytes())) { /* Got one */
			//System.out.println(stem + ">" + cf );
			BS.put(stem, new Solution (stem,pathToMask(cf)));
		}

		if (!da.existPrefix(stem.getBytes())) {
			return; /* Prune search - can't find any with this prefix */
		}

		int[][] dxdy = {{1,0}, {1,-1}, {0,-1}, {-1,-1}, {-1,0}, {-1,1}, {0,1}, {1,1}};

		for (int n[]: dxdy) {
			int x2 = x + n[0];
			int y2 = y + n[1];

			if ((x2>=0) && (y2>=0) && (y2<rows) && (x2<cols) && !cf.contains(xyToPos(y2,x2,rows)) ) {
				stem = st + String.valueOf(board[y2].charAt(x2));
				ArrayList<Integer> coo2 = new ArrayList<>();
				coo2.addAll(cf);
				coo2.add((xyToPos(y2,x2,rows)));
				SolveBoggle(x2, y2, stem, coo2);
			}
		}

	}
	
	private static int xyToPos(int row, int col, int rowlen) {
		return (row*rowlen)+(col);
	}
	
	public class Solution {
		private final String word;
		private final int mask;
		
		private Solution(String word, int mask) {
			this.word = word;
			this.mask = mask;
		}
		
		public String getWord() { return word; }
		public int getMask() { return mask; }
	}

}
