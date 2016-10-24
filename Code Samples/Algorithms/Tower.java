/*
<HTML>
<BODY>
<APPLET CODE=Tower.class WIDTH=400 HEIGHT=400>
</APPLET>
</BODY>
</HTML>
*/

import java.awt.*;
import java.applet.*;
import java.util.*;

public class Tower extends Applet {
    TowerPanel panel;
    TowerControl control;

    public void init() {
        resize(500, 450);
        setLayout(new BorderLayout());

        panel = new TowerPanel();
        add("Center", panel);
        add("South", control = new TowerControl(panel));
    }

    public void start() {
        panel.start();
    }

    public void stop() {
        panel.stop();
    }

    public boolean action(Event evt, Object arg) {
        if (arg instanceof Boolean) {
            if (((Checkbox) evt.target).getLabel().equal {
                panel.reset = ((Boolean) arg).booleanValue();
            } else{
                panel.reset = ((Boolean) arg).booleanValue();
            }
            return true;
        }
        return false;
    }
}

class Towering {
    int num_rings[] = new int[3];
    int top_size[] = new int[3];

    public Towering(int a) {
        num_rings[0] = a;
        num_rings[1] = 0;
        num_rings[2] = 0;
        top_size[0] = 40;
        top_size[1] = 1000;
        top_size[2] = 1000;
    }

    public void reset(int b) {
        num_rings[0] = b;
        num_rings[1] = 0;
        num_rings[2] = 0;
        top_size[0] = 40;
        top_size[1] = 1000;
        top_size[2] = 1000;
    }

}

class ring {
    int ring_size[] = new int[10];
    int old_x[] = new int[10];
    int which_x[] = new int[10];
    int which_y[] = new int[10];
    int order[] = new int[10];
    int which_tower[] = new int[10];

    public ring(int a) {
        for (int i = 0; i < a; i++) {
            ring_size[i] = 40 + 20 * i;
            which_x[i] = 100;
            old_x[i] = which_x[i];
            which_y[i] = 400 - 30 * (a - i);
            order[i] = a - i;
            which_tower[i] = 0;
        }
    }

    public void reset(int b) {
        for (int i = 0; i < b; i++) {
            ring_size[i] = 40 + 20 * i;
            which_x[i] = 100;
            old_x[i] = which_x[i];
            which_y[i] = 400 - 30 * (b - i);
            order[i] = b - i;
            which_tower[i] = 0;
        }
    }
}

class TowerPanel extends Panel implements Runnable {
    Thread going = null;
    int width = 500;
    int height = 400;
    int tower_height = 350;
    int tower_width = 20;
    int num_rings = 8;
    int pick_ring = 1000;
    int tempx;
    int tempy;
    boolean win = false;
    boolean reset;

    //special effect words
    String s = new String("Tower of Hanoi");
    char separated[];
    int x_coord = 0, y_coord = 0;

    Towering towers = new Towering(num_rings);
    ring rings = new ring(num_rings);

    TowerPanel() {
        setFont(new Font("TimesRoman", Font.BOLD, 40));
        setBackground(new Color(170, 170, 170));
        separated = new char[s.length()];
        s.getChars(0, s.length(), separated, 0);
    }

    public void paint(Graphics g) {
        //special words
        for (int i = 0; i < s.length(); i++) {
            x_coord = (int) (Math.random() * 10 + 15 * i + 100);
            y_coord = (int) (Math.random() * 10 + 36);
            g.setColor(new Color(200 - 10 * i, 10 * i, 10 * i));
            g.drawChars(separated, i, 1, x_coord, y_coord);
        }

        //drawing those three towers
        g.setColor(Color.red);
        g.fill3DRect(90, height - tower_height, tower_width, tower_height, true);
        g.setColor(Color.blue);
        g.fill3DRect(240, height - tower_height, tower_width, tower_height, true);
        g.setColor(Color.yellow);
        g.fill3DRect(390, height - tower_height, tower_width, tower_height, true);


        //drawing all the rings
        g.setColor(Color.black);
        for (int j = 0; j < num_rings; ++j) {
            g.setColor(new Color(20 * j + 55, 200 - 20 * j, 20 * j + 55));
            g.fill3DRect(rings.which_x[j] - rings.ri� rings.which_y[j], rings.ring_size[j], 30, true);
        }

        //Congratulation
        if (win) {
            g.setColor(Color.white);
            g.drawString("Congratulation!!!", 150, 1�
        }

    }

    public synchronized boolean mouseDown(Event evt, int x, int y) {
        for (int k = 0; k < num_rings; ++k) {
            if (rings.order[k] == towers.num_rings[rings.which_tower[k]]) {
                if ((x >= (rings.which_x[k] - rings.ring_si�
                if ((y >= rings.which_y[k]) && (y <= (rings.whi�
                pick_ring = k;
            }
        }
    }
}
 return true;
         }

public synchronized boolean mouseDrag(Event evt,int x,int y){
        if(pick_ring!=1000){
        rings.which_x[pick_ring]=x;
        rings.which_y[pick_ring]=y;
        //repaint(1,x-40,y-40,rings.ring_size[�
        repaint();
        }
        return true;
        }

public synchronized boolean mouseUp(Event evt,int x,int y){
        if(pick_ring!=1000){
        if((140>=rings.which_x[pick_ring])&&(1
        ++towers.num_rings[0];
        --towers.num_rings[rings.which_tower[pic
        rings.which_tower[pick_ring]=0;
        rings.which_x[pick_ring]=100;
        rings.order[pick_ring]=towers.num_rings[0];
        rings.which_y[pick_ring]=400-30*towers.num_rings[0];
        }
        else if((280>=rings.which_x[pick_ring])&&(240
        ++towers.num_rings[1];
        --towers.num_rings[rings.which_tower[pic
        rings.which_tower[pick_ring]=1;
        rings.which_x[pick_ring]=250;
        rings.order[pick_ring]=towers.num_rings[1];
        rings.which_y[pick_ring]=400-30*towers.num_rings[1];

        }
        else if((430>=rings.which_x[pick_ring])&&(390
        ++towers.num_rings[2];
        --towers.num_rings[rings.which_tower[pic
        rings.which_tower[pick_ring]=2;
        rings.which_x[pick_ring]=400;
        rings.order[pick_ring]=towers.num_rings[2];
        rings.which_y[pick_ring]=400-30*towers.num_rings[2];
        }
        else{
        rings.which_x[pick_ring]=rings.old_x[pick_ring];
        rings.which_y[pick_ring]=400-30*towers.num_rings[rings.which_towe�
        }
        }
        rings.old_x[pick_ring]=rings.which_x[pick_ring];
        pick_ring=1000;

        //recompute the to_size for all towers
        for(int z=0;z<num_rings;++z){
        if(rings.order[z]==towers.num_rings[rings.which_tower[z]]){
        towers.top_size[rings.which_tower[z]]=rings.ring_size[z];
        }
        }

        //see if the user wins
        for(int t=0;t<3;++t){
        if((t!=0)&&(towers.num_rings[t]==num_rings)){
        win=true;
        }
        if(towers.num_rings[t]==0){
        towers.top_size[t]=1000;
        }
        }
        repaint();
        return true;
        }

public void start(){
        if(going==null){
        going=new Thread(this);
        going.start();
        }
        }

public void stop(){
        going=null;
        }

 /*
 private Image offScreenImage;
 private Dimension offScreenSize;
 private Graphics offScreenGraphics;

 public final synchronized void update (Graphics g){
 Dimension d = size();
 if((offScreenImage == null) || (d.width !=offScreenSize.width) || (d.height != offScreenSize.height)) {
 offScreenImage = createImage(d.width, d.height);
 offScreenSize = d;
 offScreenGraphics = offScreenImage.getGraphics();
 }
 paint(offScreenGraphics);
 g.drawImage(offScreenImage, 0, 0, null);
 }

 */
public void run(){
        while(going!=null){
        try{Thread.sleep(100);}catch(InterruptedException e){}
        repaint(1,0,0,500,45);
        //repaint();
        }
        going=null;
        }


public void reset(boolean filled,int num_blocks){
        win=false;
        num_rings=num_blocks;
        towers.reset(num_rings);
        rings.reset(num_rings);
        repaint();
        }
        }

class TowerControl extends Panel {
    TextField s;
    TextField e;
    TowerPanel pa;

    public TowerControl(TowerPanel pa) {
        this.pa = pa;
        add(s = new TextField("8", 4));
        add(new Button("Reset Blocks Number"));
    }

    public boolean action(Event ev, Object arg) {
        if (ev.target instanceof Button) {
            String label = (String) arg;

            pa.reset(label.equals("Reset Blocks Number"),
                    Integer.parseInt(s.getText().trim()));

            return true;
        }

        return false;
    }
}