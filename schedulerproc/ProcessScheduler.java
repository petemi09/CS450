package schedulerproc;
import java.nio.file.attribute.UserPrincipalNotFoundException;
/**
 * @author yasiro01
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import sun.java2d.pipe.SpanShapeRenderer.Simple;

/**
 * Process scheduler
 * 
 * readyQueue is a list of processes ready for execution
 * rrQuantum is the time quantum used by round-robin algorithm
 * add() and clear() are wrappers around ArrayList methods
 */
public class ProcessScheduler {
    private final ArrayList<SimpleProcess> readyQueue;
    private final int rrQuantum;
    private double time;
    //private int weightTime;
    static private Comparator<SimpleProcess> burst;
    static private Comparator<SimpleProcess> priority;

    public ProcessScheduler() {
        this.readyQueue = new ArrayList<>();
        this.rrQuantum = 4;
        this.time = 0;
        //this.weightTime = 0;
    }
    
    static {
        burst = new Comparator<SimpleProcess>() {
            @Override
            public int compare(SimpleProcess a, SimpleProcess b) {
                if (a.getNextBurst() < b.getNextBurst())
                    return -1;
                if (a.getNextBurst() > b.getNextBurst())
                    return 1;
                return 0;
            }
        };
    }

    public void add(SimpleProcess newProcess) {
        this.readyQueue.add(newProcess);
    }

    public void clear() {
        this.readyQueue.clear();
    }

    /**
     * FCFS scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */

    public double useFirstComeFirstServe() {
        //throw new UnsupportedOperationException();
        for (int x = 0; x < this.readyQueue.size(); x++) {
            for (int  y = 0; y < x; y++) {
                this.readyQueue.get(x).weightTime = this.readyQueue.get(y).getNextBurst();
            }
            this.time = this.readyQueue.get(x).weightTime;
        }
        return (this.time/this.readyQueue.size());        
    }

    /**
     * SJF scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useShortestJobFirst() {
        //throw new UnsupportedOperationException();
        this.readyQueue.sort(burst);
        for (int x = 0; x < this.readyQueue.size(); x++) {
            for (int  y = 0; y < x; y++) {
                this.readyQueue.get(x).weightTime = this.readyQueue.get(y).getNextBurst();
            }
            this.time = this.readyQueue.get(x).weightTime;
        }
        return (this.time/this.readyQueue.size());
    }

    /**
     * Priority scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double usePriorityScheduling() {
        //throw new UnsupportedOperationException();
        this.readyQueue.sort(priority);
        for (int x = 0; x < this.readyQueue.size(); x++) {
            for (int  y = 0; y < x; y++) {
                this.readyQueue.get(x).weightTime = this.readyQueue.get(y).getNextBurst();
            }
            this.time = this.readyQueue.get(x).weightTime;
        }
        return (this.time/this.readyQueue.size());
    }

    /**
     * Round-Robin scheduling algorithm implementation
     * 
     * @return average waiting time for all processes
     */
    public double useRoundRobin() {
        //throw new UnsupportedOperationException();
        this.time = 0;
        it remain_bt[] = new int[this.readyQueue.size()];
        for (int i = 0; i < this.readyQueue.size(); i++)
            remain_bt[i] = this.readyQueue.get(i).getNextBurst();
        int t = 0;
        while(true)
        {
            boolean done = true;
            for (int i = 0; i < this.readyQueue.size(); i++) {
                if (remain_bt[i] >0)
                {
                    done = flase;
                    if (remain_bt[i] > this.rrQuantum) {
                        t += this.rrQuantum;
                        remain_bt[i] -= this.rrQuantum;
                    }
                    else
                    {
                        t = t + remain_bt[i];
                        this.readyQueue.get(i).weightTime = t - this.readyQueue.get(i).getNextBurst();
                        remain_bt[i] = 0;
                    }
                }
            }
            if (done == true)
                break;
        }
        for (int i = 0; i < this.readyQueue.size(); i++) {
            this.time = this.time + this.readyQueue.get(i).weightTime;

        }
        return ((double)this.time / (double)this.readyQueue.size());

    }
}
