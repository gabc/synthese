#include "synthese.h"
#include <QtWidgets/QApplication>

#include "Terrain.h"
#include "LibHandler.h"
#include "Creature.h"

int main(int argc, char *argv[])
{
	QApplication a(argc, argv);
	Synthese w;
	w.show();
	Terrain t = Terrain(QSize(5, 5));

	LibHandler lh = LibHandler(new QString(w.openCreature()));
	
	return a.exec();
}
