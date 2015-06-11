#include "Terrain.h"

Terrain::Terrain(QSize size){
	this->terrain = QList<Case>();

	for (int i = 0; i < size.height(); i++)
		for (int j = 0; j < size.width(); j++) {
			Case c = Case(QPoint(i, j));
			this->terrain.append(c);
		}
	this->size = size;
}

Terrain::~Terrain(){
}

QSize
Terrain::getSize(void){
	return this->size;
}

QList<Case>
Terrain::getTerrain(void){
	return this->terrain;
}
