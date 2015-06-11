#include "synthese.h"

Synthese::Synthese(QWidget *parent)
	: QMainWindow(parent)
{
	ui.setupUi(this);
	this->parent = parent;
}

Synthese::~Synthese()
{

}

QString
Synthese::openCreature(void)
{
	QString fileName = QFileDialog::getOpenFileName(this->parent);
	return fileName;
}