#!/usr/bin/env python3
"""
The *bean machine*, also known as the *Galton Board* or *quincunx*, is a device invented by Sir Francis Galton to demonstrate the central limit theorem, in particular that the normal distribution is approximate to the binomial distribution.
"""

import argparse
import random
import threading



class Board:
    """
    Class Board
    
    Contains multiple bins that collect beans
    Contains multiple levels of pegs
    """

    def __init__(self, bins: int):
        """Make a new board of the specified size"""
        self._bins = [0] * bins
        self._pegs = bins // 2

    def status(self, pos: int):
        """Print status"""
        
        #raise NotImplementedError
        #print(self._bins)

    def __len__(self):
        """Return the board size"""
        return len(self._bins)

    def __getitem__(self, idx: int):
        """Get number of beans in the specified bin"""
        return self._bins[idx]

    def __setitem__(self, idx: int, new_value: int):
        """Set number of beans in the specified bin"""
        self._bins[idx] = new_value

    @property
    def pegs(self):
        """Return number of levels of pegs"""
        return self._pegs


class Bean(threading.Thread):
    """
    Class Bean

    Data members: board, current position, probability, lock
    """

    def __init__(self, board: object, start: int, prob: float, lock: object):
        """Make a new Bean"""
        super().__init__()
        self.board = board
        self._start = start
        self.prob = 0.5
        self.lock = lock
        #raise NotImplementedError

    def move_left(self):
        """Move a bean left"""
        val = self.board.__getitem__(self._start)
        val -= 1
        self.board.__setitem__(self._start,val)
        self._start -= 1
        value = self.board.__getitem__(self._start)
        value += 1
        self.board.__setitem__(self._start,value)
        #print(val)
        
        #raise NotImplementedError

    def move_right(self):
        val = self.board.__getitem__(self._start)
        val -= 1
        self.board.__setitem__(self._start,val)
        self._start += 1
        value = self.board.__getitem__(self._start)
        value += 1
        self.board.__setitem__(self._start,value)
        #print(val)
        #raise NotImplementedError

    def run(self):
        """Run a bean through the pegs"""
        self.lock.acquire()
        for x in range(0,self.board.pegs * 2):
            y = random.choices(['l','r'],weights=[0.5,0.5],k=1)
            
            if y == ['l']:
                
                if self._start != 0:
                    self.move_left()
            elif y==['r']:
                if self._start != len(self.board)-1:
                    self.move_right()
            
        
        self.lock.release()
        
        
        
        
        #raise NotImplementedError


def main():
    """Main function"""
    # Parse command-line arguments
    print("Start")
    parser = argparse.ArgumentParser(description="Process the arguments.")
    parser.add_argument("--beans",default=1000,type=int)
    parser.add_argument("--bins",default=11,type=int)
    parser.add_argument("--start",default=5, type= int)
    parser.add_argument("--prob",default= 0.5,type=float)
    args = parser.parse_args()
    print("Beans: {}, bins: {}, start: {}, prob: {}".format(args.beans,args.bins,args.start,args.prob))
    b1 = Board(int(args.bins))
    b1.__setitem__(args.start,args.beans)
    lock = threading.Lock()
    #print(b1._bins)
    #bean1 = Bean(b1,args.start,args.prob,lock)
    #print(bean1.run())
    jobs = []
    for i in range(0,args.beans):
        jobs.append(Bean(b1,args.start,args.prob,lock))
    # print(bean1.run())
    for job in jobs:
        job.start()

    for job in jobs:
        job.join()

    st = '| '
    count = 0
    for bin in range(0, args.bins):
        st+= str(b1[bin])+' | '
        count += b1[bin]
    print(st+str(count))
    

    ###############################

    #print(b1._pegs)
    #b1.__setitem__(5,args.beans)
    #print(b1._bins)
    #print(b1.__getitem__(1))
    #print(b1.pegs)
    #print(b1.status(5))
    # Create a list of jobs
    # Create a shared lock
    # Create a board
    # Create jobs (beans)
    # Print the board status
    # Start jobs
    # Stop jobs
    # Print the board status
    print("Done")


if __name__ == "__main__":
    main()


