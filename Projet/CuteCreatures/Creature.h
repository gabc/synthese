#ifndef CREATURE_H
#define CREATURE_H

class Creature
{
protected:
    Creature();
    ~Creature();
    int aggressivite(void);
    //DNAGraph * statistiques(void);
    bool canSee(const Creature *c);
    bool canReproduce(void);
    bool canReproduceWith(const Creature *c);
    Creature * reproduceWith(const Creature *c);
    void update(void);
    bool isAlive(void);
    QRect position(void);
    //DNAChanger changeStats(void);
    int health(void);
    int hitPower(void);
    int defence(void);
    int getId(void);
    void attack(Creature *c);
    bool isCarnivore(void);
    bool isAnimal(void);
};

#endif // CREATURE_H
