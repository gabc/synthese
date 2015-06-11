#include "Creature.h"

Creature::Creature(){

}

Creature::~Creature(){

}

int
Creature::aggressivite(void){
    return 0;
}

bool
Creature::canSee(const Creature *c){
    return true;
}

bool
Creature::canReproduce(void){
    return true;
}

bool
Creature::canReproduceWith(const Creature *c){
    return true;
}

Creature*
Creature::reproduceWith(const Creature *c){
    return NULL;
}

void
Creature::update(void){}

bool
Creature::isAlive(void){
    return true;
}

QRect*
Creature::position(void){
    return NULL;
}

int
Creature::health(void){
    return 0;
}

int
Creature::hitPower(void){
    return 0;
}

int
Creature::defence(void){
    return 0;
}

int
Creature::getId(void){
    return 0;
}

void
Creature::attack(Creature *c){}

bool
Creature::isCarnivore(void){
    return true;
}

bool
Creature::isAnimal(void){
    return true;
}
