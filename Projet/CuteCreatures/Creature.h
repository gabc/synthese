#ifndef CREATURE_H
#define CREATURE_H

#include <QRect>

class Creature
{
protected:
    Creature();
    ~Creature();
    virtual int aggressivite(void);
    //virtual DNAGraph * statistiques(void);
    virtual bool canSee(const Creature *c);
    virtual bool canReproduce(void);
    virtual bool canReproduceWith(const Creature *c);
    virtual Creature * reproduceWith(const Creature *c);
    virtual void update(void);
    virtual bool isAlive(void);
    virtual QRect* position(void);
    //virtual DNAChanger changeStats(void);
    virtual int health(void);
    virtual int hitPower(void);
    virtual int defence(void);
    virtual int getId(void);
    virtual void attack(Creature *c);
    virtual bool isCarnivore(void);
    virtual bool isAnimal(void);
};

#endif // CREATURE_H
