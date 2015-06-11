#include "Case.h"

QSize Case::size = QSize(5, 5);

Case::Case(QPoint pos){
	this->pos = pos;
}

Case::~Case(){
}

QPoint
Case::getPos(void){
	return this->pos;
}

QRect
Case::getRect(void){
	return QRect(this->pos, this->size);
}

QSize
Case::getSize(void){
	return this->size;
}
