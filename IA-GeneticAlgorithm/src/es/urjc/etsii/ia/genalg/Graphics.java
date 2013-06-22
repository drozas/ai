package es.urjc.etsii.ia.genalg;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Graphics {
	int min;
	private int[] data_avg;
	private int[] data_min;
	private Display display = new Display();
	private Shell shell = new Shell(display);
	public Graphics(String name,int width,int height,int[] data_min,int[] data_avg,int min)
	{
		this.min=min;
		this.data_avg=data_avg;
		this.data_min=data_min;
		shell.setText(name);
		shell.setSize(new Point(width,height));
		createGraph();
	}
	public void start()
	{
		shell.open ();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	public void createGraph() {
		shell.setLayout(new FillLayout());
		Canvas canvas = new Canvas(shell, SWT.NONE);
		canvas.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) 
			{
				// Do some drawing
				int x=0,y=0,px,py;
				double max=7e+7;
				Rectangle rect = ((Canvas) e.widget).getBounds();
				e.gc.setBackground(e.display.getSystemColor(SWT.COLOR_WHITE));
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_RED));
				e.gc.drawText("Minimum cost", 35, 20);
				for (int i=0;i<data_min.length;i++)
				{
					px=x;
					py=y;
					x=(int)((double)i/data_min.length*(rect.width-30))+30;
					y=rect.height - (int)((double)data_min[i]/max*(rect.height-20));
					if (i>0)
						e.gc.drawLine(px, py, x, y);
				}
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_GRAY));
				y=rect.height - (int)((double)min/max*(rect.height-20));
				e.gc.drawLine(30,y,rect.width,y);
				e.gc.drawText("Minimum cost:"+min, rect.width-200, y-10);
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_GREEN));
				e.gc.drawText("Average cost", 145, 20);
				for (int i=0;i<data_min.length;i++)
				{
					px=x;
					py=y;
					x=(int)((double)i/data_min.length*(rect.width-30))+30;
					y=rect.height - (int)((double)data_avg[i]/max*(rect.height-20));
					if (i>0)
						e.gc.drawLine(px, py, x, y);
				}
				e.gc.drawFocus(30, 5, rect.width - 30, rect.height - 20);
				e.gc.setForeground(e.display.getSystemColor(SWT.COLOR_BLACK));
				for (int i=0;i<10;i++)
				{
					e.gc.drawText((int)(i*max/1e+7)+"M", 0,rect.height-(int)(i*((double)rect.height/10))-20);
					e.gc.drawText(i*data_avg.length/10+"p", (int)(i*((double)rect.width/10))+30,rect.height-15);
				}
			}
	});
}

}
