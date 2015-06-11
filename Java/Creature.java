
class Creature
{
	private QPoint pos;
	private bool m_isCarnivore;
	private bool m_isAnimal;
	private int id;
	private QSize taille;

	private Creature(){
	}

	private virtual int aggressivite(void){
	}

	private virtual DNAGraph * statistiques(void){
	}

	private virtual bool canSee(const Creature *c){
	}

	private virtual bool canReproduce(void){
	}

	private virtual bool canReproduceWith(const Creature *c){
	}

	private virtual Creature * reproduceWith(const Creature *c){
	}

	private virtual void update(void){
	}

	private virtual bool isAlive(void){
	}

	private virtual QRect* position(void){
	}

	private virtual DNAChanger changeStats(void){
	}

	private virtual int health(void){
	}

	private virtual int hitPower(void){
	}

	private virtual int defence(void){
	}

	private virtual int getId(void){
	}

	private virtual void attack(Creature *c){
	}

	private virtual bool isCarnivore(void){
	}

	private virtual bool isAnimal(void){
	}
}
