#ifndef SYNTHESE_H
#define SYNTHESE_H

#include <QtWidgets/QMainWindow>
#include <qfiledialog.h>
#include "ui_synthese.h"

class Synthese : public QMainWindow
{
	Q_OBJECT

public:
	Synthese(QWidget *parent = 0);
	~Synthese();
	QString openCreature(void);

private:
	Ui::SyntheseClass ui;
	QWidget *parent;
};

#endif // SYNTHESE_H
