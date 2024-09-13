/* DTU Course 02158 Concurrent Programming
 *   Lab 2
 *   spin5.pml
 *     Skeleton PROMELA model of mutual exlusion by coordinator
 */

#define N 3

bool enter[N];  /* Request to enter flags */
bool ok[N];     /* Entry granted flags    */

int incrit = 0; /* For easy statement of mutual exlusion */

/*
 * Below it is utilised that the first N process instances will get 
 * pids from 0 to (N-1).  Therefore, the pid can be directly used as 
 * an index in the flag arrays.
 */

active [N] proctype P()
{
	do
	::	/* First statement is a dummy to allow a label at start */
		skip; 

entry:	
		enter[_pid] = true;
		/*await*/ ok[_pid] ->

crit:	/* Critical section */
		incrit++;
		assert(incrit == 1);
		incrit--;
  	
exit: 
		ok[_pid] = false;
		
		/* Non-critical section (may or may not terminate) */
		do :: true -> skip :: break od

	od;
}


active proctype Coordinator()
{
    int i = 0;
    do
    :: true ->
            if
            :: enter[i] ->

				enter[i] = false;
                ok[i] = true;
 				ok[i] == false -> skip
                
            :: else -> skip
            fi;
          i = (i + 1) % (N)
    od;
}

ltl safety { [] (!(incrit > 1)) }
ltl fair { [] ( (P[0]@entry -> <>P[0]@crit) ) }
ltl fair1 { [] ( (P[1]@entry) -> <>  (P[1]@crit) ) }


