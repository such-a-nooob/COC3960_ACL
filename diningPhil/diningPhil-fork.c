#include <stdio.h>
#include <sys/wait.h> 
#include <semaphore.h> 

#define N 5 
#define THINKING 0 
#define HUNGRY 1 
#define EATING 2 
#define LEFT (pno + 4) % N 
#define RIGHT (pno + 1) % N 

int state[N]; 

sem_t chops[N];
sem_t mutex;   

void test(int pno) 
{ 
    if (state[pno] == HUNGRY && state[LEFT] != EATING && state[RIGHT] != EATING) 
    { 
        state[pno] = EATING; 
        printf("Philosopher %d TAKES fork %d and %d for EATING\n",  pno + 1, LEFT + 1, pno + 1); 
        sem_post(&chops[pno]); 
    } 
} 

void pickup(int pno) 
{ 
    sem_wait(&mutex); 
    state[pno] = HUNGRY; 
    printf("Philosopher %d is HUNGRY\n", pno + 1);
    test(pno); 
    sem_post(&mutex); 
    sem_wait(&chops[pno]); 
} 

void putdown(int pno) 
{ 
    sem_wait(&mutex); 
    state[pno] = THINKING; 
    printf("Philosopher %d PUTS DOWN fork %d and %d\n", pno + 1, LEFT + 1, pno + 1);
    printf("Philosopher %d is THINKING\n", pno + 1); 
    test(LEFT); 
    test(RIGHT); 
    sem_post(&mutex); 
} 

void initialise(int pno) 
{ 
    pickup(pno); 
    putdown(pno); 
}

int main() 
{ 
    int i; 
    pid_t phil;

    for (i = 0; i < N; i++) 
        sem_init(&chops[i], 0, 0); 

    sem_init(&mutex, 0, 1); 

    for (i = 0; i < N; i++) 
    {
        phil = fork();
        printf("Philosopher %d is THINKING\n", i + 1);
    
        if (phil == 0) 
        {
            initialise(i);
            exit(0);
        }
    }

    for (i = 0; i < N; i++) {
        wait(NULL);
    }

    for (i = 0; i < N; i++) 
        sem_destroy(&chops[i]);
    sem_destroy(&mutex);

    return 0; 
}
