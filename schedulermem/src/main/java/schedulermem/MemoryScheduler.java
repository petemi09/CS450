package schedulermem;
import java.util.ArrayList;
import java.util.HashMap; 
import java.util.HashSet; 
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Mitchell Petellin
 */
public class MemoryScheduler {

    private int pageFaultCount;
    private final int frames;

    public MemoryScheduler(int frames) {
        this.pageFaultCount = 0;
        this.frames = frames;
        //makes number of frames
    }

    public int getPageFaultCount() {
        return this.pageFaultCount;
    }

    public void useFIFO(String referenceString) {
        int capacity = this.frames;
        String[] numberStrs = referenceString.split(",");
        int[] pages = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++)
        {
            pages[i] = Integer.parseInt(numberStrs[i]);
        } 
        int n = pages.length;
        HashSet<Integer> s = new HashSet<>(capacity); 
        Queue<Integer> indexes = new LinkedList<>(); 
        int page_faults = 0; 
        for (int i=0; i<n; i++) { 
            if (s.size() < capacity) { 
                if (!s.contains(pages[i])) { 
                    s.add(pages[i]); 
                    page_faults++; 
                    indexes.add(pages[i]); 
                } 
            } else { 
                if (!s.contains(pages[i])) {  
                    int val = indexes.peek(); 
                    indexes.poll();  
                    s.remove(val); 
                    s.add(pages[i]); 
                    indexes.add(pages[i]); 
                    page_faults++; 
                } 
            } 
        } 
        System.out.println("Total Number of Page Faults right here FIFO: " + page_faults);
        this.pageFaultCount = page_faults;
        //return page_faults; 
    } 



    public void useOPT(String referenceString) {
        String[] numberStrs = referenceString.split(",");
        int[] reference = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++) {
            reference[i] = Integer.parseInt(numberStrs[i]);
        }
        int frames = this.frames;
        int pointer = 0;
        int numFault = 0;
        int ref_len;
        boolean isFull = false;
        int buffer[];
        boolean hit[];
        int fault[];
        int mem_layout[][];
        ref_len = reference.length;
        mem_layout = new int[ref_len][frames];
        buffer = new int[frames];
        hit = new boolean[ref_len];
        fault = new int[ref_len];
        for(int i = 0; i < ref_len; i++) {
            int search = -1;
            for(int j = 0; j < frames; j++) {
                if(buffer[j] == reference[i]) {
                    search = j;
                    hit[i] = true;
                    fault[i] = numFault;
                    break;
                }
            }
            if(search == -1) {
                if(isFull) {
                    int index[] = new int[frames];
                    boolean index_flag[] = new boolean[frames];
                    for(int j = i + 1; j < ref_len; j++) {
                        for(int k = 0; k < frames; k++) {
                            if((reference[j] == buffer[k]) && (index_flag[k] == false)) {
                                index[k] = j;
                                index_flag[k] = true;
                                break;
                            }
                        }
                    }
                    int max = index[0];
                    pointer = 0;
                    if(max == 0) {
                        max = 200;
                    }
                    for(int j = 0; j < frames; j++) {
                        if(index[j] == 0) {
                            index[j] = 200;
                        }
                        if(index[j] > max) {
                            max = index[j];
                            pointer = j;
                        }
                    }
                }
                buffer[pointer] = reference[i];
                numFault++;
                fault[i] = numFault;
                if(!isFull) {
                    pointer++;
                    if(pointer == frames) {
                        pointer = 0;
                        isFull = true;
                    }
                }
            }
        }
        System.out.println("Total Number of Page Faults right here OPT: " + numFault);
        this.pageFaultCount = numFault;
    }



    public void useLRU(String referenceString) {
        int capacity = this.frames;
        String[] numberStrs = referenceString.split(",");
        int[] pages = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++)
        {
            pages[i] = Integer.parseInt(numberStrs[i]);
        } 
        int n = pages.length;
        
        ArrayList<Integer> s=new ArrayList<>(capacity); 
        int count=0; 
        int page_faults=0; 
        for(int i:pages) { 
            if(!s.contains(i)) { 
                if(s.size()==capacity){ 
                    s.remove(0); 
                    s.add(capacity-1,i); 
                } else 
                    s.add(count,i);  
                    page_faults++; 
                    ++count; 
                } else {  
                    s.remove((Object)i); 
                    s.add(s.size(),i);          
                } 
        } 
        System.out.println("Total Number of Page Faults right here LRU: " + page_faults);
        this.pageFaultCount = page_faults;
    }

}
