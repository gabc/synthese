#ifndef CASE_H
#define CASE_H

#include <QPoint>
#include <QSize>
#include <QRect>

class Case
{
 public:
	Case(QPoint pos/* , Type type */);
	~Case();

	QPoint getPos(void);
	QRect getRect(void);
	QSize getSize(void);

 private:
	QPoint pos;
	static QSize size;
	/* Type type; */
};

#endif
