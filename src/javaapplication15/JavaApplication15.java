
package javaapplication15;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class JavaApplication15 extends JFrame implements ActionListener{
    public static int[][] saha=new int [50][50];
    public static int saha_boyutu=50*50;
 
    
    static JTextField textfield_yuzde;// JTextField Yuzde deüeri
    static JTextField textfield_baslangic;// JTextField başlangıç deüeri
    static JTextField textfield_bitis;// JTextField bitiş deüeri
    static JFrame f;// JFrame
    static JButton b;// JButton
    
    public static void engeleata(double yuzde, int baslangic,int bitis){
        // 0 demek engel yok yani beyaz
        // 1 demek engel var yani kirmizi
        // 2 demek başlangıç noktası demek
        // 3 demek bitiş noktası demek
        
        // en başta sahayı tamamen 0 ile dolduruyoruz yani engel yok
        for (int i = 0; i < saha.length; i++) { 
            for (int j = 0; j < saha.length; j++) {
                saha[i][j]=0;
            }
        }
        
        // BAŞLANGIÇ VE BİTİŞİN ATANMASI
        int baslangic_y=baslangic%50;
        int baslangic_x=baslangic/50;
        saha[baslangic_y][baslangic_x]=2;
        
        int bitis_y=bitis%50;
        int bitis_x=bitis/50;
        saha[bitis_y][bitis_x]=3;
        
        System.out.println("Başlangıç noktası :"+baslangic_y+","+baslangic_x);
        System.out.println("Bitiş noktası :"+bitis_y+","+bitis_x);
        
        
        // ENGELLERİN ATANMASI
        int engel_sayisi=(int) round(saha_boyutu*yuzde);  // ENGEL SAYISI
        System.out.println("Saha boyutu:"+saha_boyutu);
        System.out.println(engel_sayisi+" tane engel vardir");
        int engel_sayac=0;
        
        // ENGELLERİN RASTGELE ATANMASI
        // Engel sayisi kadar whileda dolas ve rastgele gelen x y degerlerine engel ata
        // Eger random x y degerlerinde daha onceden engel varsa atama yapma ve tekrar random x ve y degeri bul
        while(engel_sayac <engel_sayisi){
            Random x=new Random();
            int x_engel=x.nextInt(50);
            int y_engel=x.nextInt(50);
            if(saha[y_engel][x_engel]==0){
                saha[y_engel][x_engel]=1;
                engel_sayac++;
            }
            
        }
        
        
        System.out.println("SAHA BİLGİLERİ:");
        for (int k = 0; k < saha.length; k++) { 
            for (int j = 0; j < saha.length; j++) {
                System.out.print(saha[k][j]+" ");
            }
            System.out.println("");
            
        }
    }
    public static void engelleri_dosyaya_yaz(){
        // Engellerin dosyaya yazdırılması
        File file = new File("engel.txt");//proje içinde engel.txt adında bir txt oluşturun.
        try(BufferedWriter br = new BufferedWriter(new FileWriter(file))){
            for (int i = 0; i < saha.length; i++) { 
                for (int j = 0; j < saha.length; j++) {
                    if(saha[i][j]==1){ // Engel var mi diye kontrol eder varsa dosyaya yazar
                        br.write("("+i+","+j+",K)");
                        br.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to read file " +file.toString());
        }
    }
    
    
    public static void main(String[] args) {
        
        new JavaApplication15(); // ALTTAKİ CONSTRUCTOR ÇAĞRILIR
        
    }
    public JavaApplication15(){
        this.setSize(1100,1100);
        this.setTitle("Saha");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f = new JFrame("textfield");
        f.setSize(600,600);
        f.setTitle("Girdiler");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         // create a label to display text
        // create a new button
        b = new JButton("submit");
        // create a object of the text class
 
        // addActionListener to button
        b.addActionListener(this);
 
        // create a object of JTextField with 16 columns
        textfield_yuzde = new JTextField("Yüzde değerini giriniz (0.3 şeklinde)");
        textfield_baslangic = new JTextField("Başlangıç konumunu giriniz");
        textfield_bitis = new JTextField("Bitiş konumunu giriniz");
 
        // create a panel to add buttons and textfield
        JPanel p = new JPanel();
 
        // KULLANICIDAN GİRDİLERİ ALIYORUZ
        p.add(textfield_yuzde);
        p.add(textfield_baslangic);
        p.add(textfield_bitis);
        p.add(b);
 
        // add panel to frame
        f.add(p);
        f.setVisible(true);
        
        //this.add(new sahacizdir(), BorderLayout.CENTER);
        
        //this.setVisible(true);//ekranda görünmesi için
    }
    class sahacizdir extends JComponent{
        @Override
        public void paint(Graphics g){
            int i,j;
            Graphics2D graf2=(Graphics2D)g;
            graf2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for( i=0;i<50;i++){
                for( j=0;j<50;j++){
                    graf2.setPaint(Color.RED); 
                    Shape dikdörtgen=new Rectangle2D.Float(100+i*15,100+j*15,15,15);//15e 15 kareler ile tahtayı çiz
                    if(saha[i][j]==1) {// engel var mı kontrol et
                        graf2.setPaint(Color.RED);         // Engel için kırmızı
                        g.fillRect(100+j*15,100+i*15,15,15);    // Engel varsa engeli çiz. (İÇİNİ KIRMZI BOYA)
                    }
                    else if(saha[i][j]==2){   // Başlangıç noktasını kontrol et
                        graf2.setPaint(Color.BLUE);         // Başlangıç için mavi
                        g.fillRect(100+j*15,100+i*15,15,15);    // Başlangıç varsa Başlangıçı çiz.(İÇİNİ MAVİ BOYA)
                    }
                    else if(saha[i][j]==3){
                        graf2.setPaint(Color.GREEN);         // Bitiş için yeşil
                        g.fillRect(100+j*15,100+i*15,15,15);    // Bitişİ ÇİZ. (İÇİNİ YEŞİL BOYA)
                    }
                    graf2.draw(dikdörtgen);     
                }
            }
            
            
        }
    }
    public void actionPerformed(ActionEvent e) // Butona basıldıktan sonra yapılacaklar
        {
            String s = e.getActionCommand(); // Basılan butondan değer alınır
            if (s.equals("submit")) {        // submite basıldıysa
                // set the text of the label to the text of the field
                double yuzde= Double.parseDouble(textfield_yuzde.getText()); // double olarak yüzde alınır
                int baslangic_noktasi=Integer.parseInt(textfield_baslangic.getText()); // int olarak başlangıcı al
                int bitis_noktasi=Integer.parseInt(textfield_bitis.getText());         // int olarak bitişi al
                System.out.println("Yuzde değeri:"+yuzde);
                if(yuzde>=0.3){
                    engeleata(yuzde,baslangic_noktasi,bitis_noktasi);
                    JFrame frame = new JFrame("textfield");
                    frame.setSize(1100,1100);
                    frame.setTitle("Saha");
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.add(new sahacizdir(), BorderLayout.CENTER);
                    frame.setVisible(true);
                    engelleri_dosyaya_yaz();
                    textfield_yuzde.setText("  ");
                }
                else{
                    textfield_yuzde.setText("Girilen değer >=0.3 olmalı");
                }
                
                // set the text of field to blank
                //t.setText("  ");
            }
        }
}
