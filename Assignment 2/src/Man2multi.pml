# define N 8

int up = 0;
int down = 0;

int upSem = 1;
int downSem = 1;

// Historic variables
int upCrit = 0;
int downCrit = 0;

//  P and V semaphores 
inline P(mutex) {atomic{ mutex > 0 -> mutex--}}
inline V(mutex) {mutex++;}

inline enter(no) {
    if
    :: no < 5 -> {
        P(downSem);
        if
        :: down == 0 -> P(upSem);
        :: else -> skip;
        fi;
        down++;
        V(downSem);
    }
    :: else -> {
        P(upSem);
        if
        :: up == 0 -> P(downSem);
        :: else -> skip;
        fi;
        up++;
        V(upSem);
    }
    fi;
}

inline leave(no) {
    if
    :: no < 5 -> {
        down--;
        if
        :: down == 0 -> V(upSem);
        :: else -> skip;
        fi;
    }
    :: else -> {
        up--;
        if
        :: up == 0 -> V(downSem);
        :: else -> skip;
        fi;
    }
    fi;
}


// cars with no<5 go down, cars with no >=5 go up
active [N] proctype drive(){
	do
	:: true ->
		enter(_pid);
		
		if
		:: _pid < 5 ->downCrit++;
		:: _pid >= 5 ->upCrit++;
		fi;
		
		if
		:: _pid < 5 -> downCrit--;
		:: _pid >= 5 -> upCrit--;
		fi;
		
		leave(_pid);	
	od	
}

// Safety property
active proctype SafetyProperty(){
	(upCrit > 0 && downCrit > 0) -> assert(false);
}
