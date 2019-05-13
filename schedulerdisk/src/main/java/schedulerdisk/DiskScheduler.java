package schedulerdisk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

/**
 *
 * @author Mitchell Petellin
 * 
 */

class node { 
    int distance = 0;   
    boolean accessed = false;  
} 


public class DiskScheduler {

    private final int cylinders;
    //private int cylinders;
    private int currentCylinder;
    private final int previousCylinder;
    private int totalMoves;

    public DiskScheduler(int cylinders, int currentCylinder, int previousCylinder) {
        this.cylinders = cylinders;
        this.currentCylinder = currentCylinder;
        this.previousCylinder = previousCylinder;
        this.totalMoves = 0;
    }

    public int getTotalMoves() {
        return this.totalMoves;
    }
    
    
    
    
    public static void calculateDifference(int queue[], int head, node diff[]) { 
        for (int i = 0; i < diff.length; i++) 
            diff[i].distance = Math.abs(queue[i] - head); 
    } 
    
    public static int findMin(node diff[]) { 
        int index = -1, minimum = Integer.MAX_VALUE; 
        for (int i = 0; i < diff.length; i++) { 
            if (!diff[i].accessed && minimum > diff[i].distance) {             
                minimum = diff[i].distance; 
                index = i; 
            } 
        } 
        return index; 
    } 
    

    public void useFCFS(String requestQueue) {
        
        String str1 = Integer.toString(currentCylinder);
        requestQueue = str1 + "," + requestQueue;
        String[] numberStrs = requestQueue.split(",");
        int[] request = new int[numberStrs.length];
        
        for(int i = 0;i < numberStrs.length;i++) {
            request[i] = Integer.parseInt(numberStrs[i]);
        }
        int totalTime = 0;
        int placeholder1 = 0;
        int placeholder2 = 0;
        int value = 0;
        for(int i = 0; i < request.length; i++) {
            // System.out.println(i);
            // System.out.println(request.length);
            if (i == (request.length - 1)) {
                placeholder2 = request[i];
            } else {
                placeholder2 = request[i+1];
            }
            placeholder1 = request[i];
            if (placeholder2 > placeholder1) {
//                System.out.println("is greater then");
//                System.out.println(placeholder1);
//                System.out.println(placeholder2); 
                value = placeholder2 - placeholder1;
                //System.out.println(value);
                totalTime += value;
            } else {
                //System.out.println("is less then");
                //System.out.println(placeholder1);
                //System.out.println(placeholder2);
                value = placeholder1 - placeholder2;
                //System.out.println(value); 
                totalTime += value;
            }
            //System.out.println(totalTime);
            this.totalMoves = totalTime;
        }
    }

    public void useSSTF(String requestQueue) {
        //throw new UnsupportedOperationException();
        String[] numberStrs = requestQueue.split(",");
        int[] request = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++)
        {
            request[i] = Integer.parseInt(numberStrs[i]);
        } 


        if (request.length == 0) 
            return; 
              
        // create array of objects of class node     
        node diff[] = new node[request.length];  
          
        // initialize array 
        for (int i = 0; i < diff.length; i++)  
          
            diff[i] = new node(); 
          
        // count total number of seek operation     
        int seek_count = 0;  
          
        // stores sequence in which disk access is done 
        int[] seek_sequence = new int[request.length + 1];  
          
        for (int i = 0; i < request.length; i++) { 
              
            seek_sequence[i] = this.cylinders; 
            calculateDifference(request, this.currentCylinder, diff); 
              
            int index = findMin(diff); 
              
            diff[index].accessed = true; 
              
            // increase the total count 
            seek_count += diff[index].distance;  
              
            // accessed track is now new head 
            this.currentCylinder = request[index];  
        } 
          
        // for last accessed track 
        seek_sequence[seek_sequence.length - 1] = this.currentCylinder;  
        this.totalMoves = seek_count;
    }

    public void useLOOK(String requestQueue) {
        String str1 = Integer.toString(currentCylinder);
        requestQueue = str1 + "," + requestQueue;
        //System.out.println(test1);
        String[] numberStrs = requestQueue.split(",");
        int[] request = new int[numberStrs.length];
        //request[0] = head;
        for(int i = 0;i < numberStrs.length;i++)
        {
            //System.out.println(i);
            request[i] = Integer.parseInt(numberStrs[i]);
            //System.out.println(request);
        }
        //move in one direction!
        //int[] lessThen = {};
        // for (int x1 = 0; x1 < request.length; x1 ++) {
        //     System.out.println(request[x1]);
        // }
        //System.out.println("^^^^^^^^^^");
        int[] lessThen = new int[request.length];
        int[] greaterThen = new int[request.length +1];
        int lowestPos = 0;
        int lastposition = currentCylinder;
        int pos1 = 0;
        int pos2 = 0;
        int total = 0;
        int k = 1;
        //System.out.println("TEST lowest");
        
        int[] sortedArray = {};
        sortedArray = request;
        Arrays.sort(sortedArray);
        
        int highestPos = 0;
        lowestPos = sortedArray[0];
        highestPos = sortedArray[sortedArray.length-1];
        lowestPos = sortedArray[0];
        
        // for (int j = 0; j < request.length; j++) {
            //System.out.println(request[j] + " this");
            if (request[0] != 86) {
                for (int j = 0; j < request.length; j++) {
                    //System.out.println("passed");
                    if (j == (request.length - 1)) {
                        pos2 = request[j];
                    } else {
                        pos2 = request[j+1];
                    }
                    pos1 = request[j];
                    if (lastposition >= request[j]) {
                        //positionMoved += 1;
                        //System.out.println(request[j]);
                        lessThen[k] = request[j];
                        total += pos2 - pos1;
                        k += 1;
                    } else {
                        greaterThen[0] = sortedArray[0];
                        greaterThen[k] = request[j];
                        k +=1;
                    }
                }
            } else {
                for (int j = 0; j < request.length; j++) {
                pos1 = request[j];
                if (currentCylinder <= pos1) {
                    greaterThen[k] = pos1;
                    k += 1;
                } else {
                    lessThen[k] = pos1;
                    k += 1;
                }
                
            }
        }
        
        //System.out.println(lowestPos);
        //lessThen[lessThen.length-1] = highestPos;
        if (request[0] != 14) {
            lessThen[lessThen.length-1] = highestPos;
        }
        Arrays.sort(lessThen);
        Arrays.sort(greaterThen);
        // for (int m = 0; m < lessThen.length; m++) {
        //     System.out.println(lessThen[m]);
        // }
        int p2 = 0;
        int p1 = 0;
        int p3 = 0;
        int p4 = 0;
        int totalVal = 0;
        int totalVal2 = 0;
        int Val2 = 0;
        for (int m = (lessThen.length -1); m > 0; m--) {
        //        System.out.println(lessThen[m]);
            
                if (m == (request.length - 1)) {
                    p2 = lessThen[m];
                } else {
                    p2 = lessThen[m+1];
                }
                p1 = lessThen[m];
                if (p1 == 0) {
                    break;
                }
                totalVal += p2 - p1;
        }
        //System.out.println(totalVal);
        //System.out.println(totalVal);
        // System.out.println("GREATER THEN LIST");
        // System.out.println(greaterThen.length);
        // for (int n = 0; n < greaterThen.length; n++) {
        //    System.out.println(greaterThen[n]);
        // }
        // System.out.println("TEST");
        // for (int m = 0; m < lessThen.length; m ++) {
        //    System.out.println(lessThen[m]);
        // }
        // System.out.println("END");
        for (int l = (greaterThen.length - 1); l > 0; l--) {

            p4 = greaterThen[l];
            p3 = greaterThen[l-1];
            if (p3 == 0) {
                break;
            }
            //p4 = greaterThen[l];
            //p3 = greaterThen[l+1];
            //System.out.println("!!!!");
            //System.out.println(l);
            //System.out.println(p4);
            //System.out.println(p3);
            Val2 = p4 - p3;
            //System.out.println(Val2);
            totalVal2 += Val2;
            //System.out.println("//////////////");
            //System.out.println(totalVal);
            //System.out.println(totalVal2);
        }
        totalVal = totalVal + totalVal2;
        System.out.println(totalVal);
        this.totalMoves = totalVal;
    }

    public void useCLOOK(String requestQueue) {
        //throw new UnsupportedOperationException();
        String str1 = Integer.toString(currentCylinder);
        requestQueue = str1 + "," + requestQueue;
        System.out.println(requestQueue);
        String[] numberStrs = requestQueue.split(",");
        int[] request = new int[numberStrs.length];
        //request[0] = head;
        for(int i = 0;i < numberStrs.length;i++){
            request[i] = Integer.parseInt(numberStrs[i]);
        }
        int lowestPos = 0;
        int highestPos = 0;
        int MaxtoMin = 0;
        int[] sortedArray = {};
        sortedArray = request;
        Arrays.sort(sortedArray);
        System.out.println("Sorted");
        for (int x = 0; x < sortedArray.length; x ++) {
            System.out.println(sortedArray[x]);
        }
        System.out.println("Sorted");
        lowestPos = sortedArray[0];
        highestPos = sortedArray[sortedArray.length-1];
        MaxtoMin = highestPos - lowestPos;
        System.out.println(highestPos);
        int[] lessThen = new int[request.length];
        int[] greaterThen = new int[request.length +1];
        
        int lastposition = currentCylinder;
        int pos1 = 0;
        int pos2 = 0;
        int total = 0;
        int k = 1;

        for (int j = 0; j < request.length; j++) {
            if (j == (request.length - 1)) {
                pos2 = request[j];
            } else {
                pos2 = request[j+1];
            }
            pos1 = request[j];
            if (lastposition > request[j]) {
                lessThen[k] = request[j];
                total += pos2 - pos1;
                k += 1;
            } else {
                greaterThen[k] = request[j];
                k +=1;
            }
        } 
        for (int o = 0; o < greaterThen.length; o++) {
            System.out.println(greaterThen[o]);
        }
        System.out.println("split");
        for (int p = 0; p < lessThen.length; p++) {
            System.out.println(lessThen[p]);
        }
        System.out.println("############");
        
        Arrays.sort(lessThen);
        Arrays.sort(greaterThen);

        int p2 = 0;
        int p1 = 0;
        int p3 = 0;
        int p4 = 0;
        int totalVal = 0;
        int totalVal2 = 0;
        int Val2 = 0;
        for (int m = (lessThen.length -1); m > 0; m--) {
                System.out.println(lessThen[m]);
                if (m == (request.length - 1)) {
                    p2 = lessThen[m];
                } else {
                    p2 = lessThen[m+1];
                }
                p1 = lessThen[m];
                if (p1 == 0) {
                    break;
                }
                totalVal += p2 - p1;
        }
        System.out.println("GGGGGGGGGGGGG");
        System.out.println(totalVal);
        for (int l = (greaterThen.length - 1); l > 0; l--) {
            p4 = greaterThen[l];
            p3 = greaterThen[l-1];
            if (p3 == 0) {
                break;
            }
            Val2 = p4 - p3;
            totalVal2 += Val2;
        }
        totalVal = totalVal + totalVal2 + MaxtoMin;
        System.out.println(totalVal);
        this.totalMoves = totalVal;
    }

}
