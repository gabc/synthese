#ifndef TERRAIN_H
#define TERRAIN_H

#include "Case.h"
#include <QList>

class Terrain
{
 public:
	Terrain(QSize size);
	~Terrain();

	QSize getSize(void);
	QList<Case> getTerrain(void);
	
 private:
	QSize size;
	QList<Case> terrain;
};

#endif
