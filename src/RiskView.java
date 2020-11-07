import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class RiskView extends JFrame implements RiskListener{

    private RiskMap m;

    private JButton startGame;
    private JButton attackButton;
    private JButton passTurn;

    private JTextArea consoleUpdate;

    private JLabel askForCountPlayers;
    private JLabel askName;
    private JLabel chooseCountry;
    private JLabel adjacentCountry;

    private String[] playerNum= {"2","3","4","5","6"};
    private ArrayList<String> playerNames;
    private JComboBox playerCountList;

    private JPanel mainCont;
    private JPanel newGameScene;
    private JPanel mainGameScene;

    private JPanel askCount;
    private JPanel consolePanel;
    private JPanel mapPanel;
    private JPanel actionPanel;
    private JPanel infoPanel;

    private JPanel northAmericaPanel;
    private JPanel southAmericaPanel;
    private JPanel europePanel;
    private JPanel africaPanel;
    private JPanel asiaPanel;
    private JPanel australiaPanel;

    private JScrollPane consoleMove;
    private JScrollPane mapScroll;
    private JScrollPane selectedCountryScrollPane;
    private JScrollPane connectedCountryScrollPane;

    private JList selectedCountries;
    private JList connectedCountries;

    private RiskModel ownCountries;
    private RiskModel adjacentCountries;


    private JTextField p1;
    private JTextField p2;
    private JTextField p3;
    private JTextField p4;
    private JTextField p5;
    private JTextField p6;
    private ArrayList<JTextField> nameFields;

    private CardLayout c1;
    private BorderLayout b1;
    private GridBagLayout mainGameLayout;
    private GridBagLayout consoleLayout;
    private GridBagLayout actionLayout;
    private GridBagLayout infoLayout;

    private GridBagConstraints a1;
    private GridBagConstraints a2;

    private ImageIcon mapImage;

    public RiskView(){

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        b1 = new BorderLayout();
        c1 = new CardLayout(5,5);

        m = new RiskMap();

        mainGameLayout = new GridBagLayout();
        consoleLayout = new GridBagLayout();
        actionLayout = new GridBagLayout();
        infoLayout = new GridBagLayout();

        attackButton = new JButton("Attack");
        passTurn = new JButton("Pass Turn");

        a1 = new GridBagConstraints();
        a1.fill = GridBagConstraints.BOTH;

        a2 = new GridBagConstraints();

        startGame = new JButton("Start the fun!");
        startGame.setEnabled(true);

        mapImage = new ImageIcon("src/mapRisk.png");

        northAmericaPanel = new JPanel();
        southAmericaPanel = new JPanel();
        europePanel = new JPanel();
        africaPanel = new JPanel();
        asiaPanel = new JPanel();
        australiaPanel = new JPanel();

        consoleMove = new JScrollPane();
        mapScroll = new JScrollPane(new JLabel(mapImage));

        selectedCountries = new JList();
        connectedCountries = new JList();



        mainCont = new JPanel(c1);
        newGameScene = new JPanel(b1);
        mainGameScene = new JPanel(mainGameLayout);
        consolePanel = new JPanel(consoleLayout);
        actionPanel = new JPanel(actionLayout);
        infoPanel = new JPanel(infoLayout);


        mapPanel = new JPanel();

        consoleUpdate = new JTextArea();

        askCount = new JPanel(new GridBagLayout());


        p1 = new JTextField("Napoleon",3);
        p2 = new JTextField("Columbus",3);
        p3 = new JTextField(3);
        p4 = new JTextField(3);
        p5 = new JTextField(3);
        p6 = new JTextField(3);

        nameFields = new ArrayList<>();

        nameFields.add(p1);
        nameFields.add(p2);
        nameFields.add(p3);
        nameFields.add(p4);
        nameFields.add(p5);
        nameFields.add(p6);

        p1.setFont(new Font("SansSerif", Font.BOLD, 14));
        p2.setFont(new Font("SansSerif", Font.BOLD, 14));
        p3.setFont(new Font("SansSerif", Font.BOLD, 14));
        p4.setFont(new Font("SansSerif", Font.BOLD, 14));
        p5.setFont(new Font("SansSerif", Font.BOLD, 14));
        p6.setFont(new Font("SansSerif", Font.BOLD, 14));

        p1.setEditable(true);
        p2.setEditable(true);
        p3.setEditable(false);
        p4.setEditable(false);
        p5.setEditable(false);
        p6.setEditable(false);

        selectedCountryScrollPane = new JScrollPane();
        connectedCountryScrollPane = new JScrollPane();

        newGameScene.setBorder(new EmptyBorder(6,6,6,6));
        mainGameScene.setBorder(new EmptyBorder(6,6,6,6));

        mainCont.add(newGameScene,"startScene");
        mainCont.add(mainGameScene,"secondScene");

        newGameScene.add(askCount,BorderLayout.CENTER);

        playerNames = new ArrayList<>();
        askName = new JLabel("Enter the player names");
        playerCountList = new JComboBox(playerNum);
        askForCountPlayers = new JLabel("How many players? ");

        newGameScene.setBackground(Color.BLACK);
        askCount.setBackground(Color.ORANGE);

        startBackgroundMusic("src/BackgroundMusic.wav");
        initializeStart();
        c1.show(mainCont, "startScene");

        this.add(mainCont);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }

    public void initializeStart(){

        this.setTitle("Welcome to RISK: GLOBAL DOMINATION");
        this.setSize(new Dimension(450,400));
        this.setResizable(false);

        a1.insets = new Insets(6,6,6,6);
        a1.gridx = 0;
        a1.gridy = 0;
        a1.anchor = GridBagConstraints.NORTHWEST;
        askCount.add(askForCountPlayers, a1);

        a1.gridx = 0;
        a1.gridy = 1;
        a1.ipady = 5;
        a1.ipadx = 5;
        a1.anchor = GridBagConstraints.NORTHWEST;
        a1.weightx = 1;
        a1.weighty = 1;
        askCount.add(playerCountList,a1);

        a1.gridx = 1;
        a1.gridy = 0;
        a1.ipadx = 0;
        a1.ipady = 0;
        a1.anchor = GridBagConstraints.NORTHWEST;
        a1.weighty = 1;
        a1.weightx = 1;
        askCount.add(askName,a1);

        a1.gridx = 1;
        a1.gridy = 1;
        a1.anchor = GridBagConstraints.NORTHWEST;
        a1.weightx = 1;
        a1.weighty = 1;
        a1.ipady = 5;
        a1.ipadx = 5;

        askCount.add(p1,a1);
        a1.gridy = 2;
        askCount.add(p2,a1);
        a1.gridy = 3;
        askCount.add(p3,a1);
        a1.gridy = 4;
        askCount.add(p4,a1);
        a1.gridy = 5;
        askCount.add(p5,a1);
        a1.gridy = 6;
        askCount.add(p6,a1);

        a1.gridx = 2;
        a1.gridy = 3;
        a1.ipadx = 0;
        a1.ipady = 0;
        a1.anchor = GridBagConstraints.CENTER;
        askCount.add(startGame, a1);
    }

    public void startBackgroundMusic(String musicPath){

        try{
            File musicFile = new File(musicPath);
            if(musicFile.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
                Clip loop = AudioSystem.getClip();
                loop.open(audioInput);
                loop.start();
                FloatControl volume = (FloatControl) loop.getControl(FloatControl.Type.MASTER_GAIN);
                volume.setValue(-5.0f);
            }
        }
        catch(Exception e){
            System.out.println("Music file not found, no music to be played :(");
        }
    }

    public void setPlayerCount(ItemListener itemE){
        playerCountList.addItemListener(itemE);
    }

    public int getPlayerCount(){

        int index = playerCountList.getSelectedIndex();
        switch(index){
            case 0:
                return 2;
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 5;
            case 4:
                return 6;
        }
        return -1;
    }

    public void setNameFields(int playerCount, boolean enable, boolean disable){

        if(playerCount == 2) {

            p1.setEditable(enable);
            p2.setEditable(enable);
            p1.setText("Napoleon");
            p2.setText("Columbus");

            p3.setEditable(disable);
            p4.setEditable(disable);
            p5.setEditable(disable);
            p6.setEditable(disable);

            p3.setText("");
            p4.setText("");
            p5.setText("");
            p6.setText("");
        }
        if(playerCount == 3){

            p1.setEditable(enable);
            p2.setEditable(enable);
            p3.setEditable(enable);

            p1.setText("Napoleon");
            p2.setText("Columbus");
            p3.setText("Caesar");

            p4.setEditable(disable);
            p5.setEditable(disable);
            p6.setEditable(disable);
            p4.setText("");
            p5.setText("");
            p6.setText("");
        }
        if(playerCount == 4){
            p1.setEditable(enable);
            p2.setEditable(enable);
            p3.setEditable(enable);
            p4.setEditable(enable);

            p1.setText("Napoleon");
            p2.setText("Columbus");
            p3.setText("Caesar");
            p4.setText("Gustavus");

            p5.setEditable(disable);
            p6.setEditable(disable);
            p5.setText("");
            p6.setText("");
        }
        if(playerCount == 5){
            p1.setEditable(enable);
            p2.setEditable(enable);
            p3.setEditable(enable);
            p4.setEditable(enable);
            p5.setEditable(enable);

            p1.setText("Napoleon");
            p2.setText("Columbus");
            p3.setText("Caesar");
            p4.setText("Gustavus");
            p5.setText("Genghis");

            p6.setEditable(disable);
            p6.setText("");
        }
        if(playerCount == 6){
            p1.setEditable(enable);
            p2.setEditable(enable);
            p3.setEditable(enable);
            p4.setEditable(enable);
            p5.setEditable(enable);
            p6.setEditable(enable);

            p1.setText("Napoleon");
            p2.setText("Columbus");
            p3.setText("Caesar");
            p4.setText("Gustavus");
            p5.setText("Genghis");
            p6.setText("Zulqarnain");
        }
    }

    public void setPlayerNames(){

        playerNames.add(p1.getText());
        playerNames.add(p2.getText());
        playerNames.add(p3.getText());
        playerNames.add(p4.getText());
        playerNames.add(p5.getText());
        playerNames.add(p6.getText());
    }

    public void setStartGame(ActionListener e){
        startGame.addActionListener(e);
    }

    public void switchView(){
        c1.show(mainCont,"secondScene");
    }

    public void setCountry(String name){

    }

    public void startNewGame(){

        this.setSize(new Dimension(1980,1020));

        a2.fill = GridBagConstraints.BOTH;
        a2.anchor = GridBagConstraints.LINE_START;
        a2.insets = new Insets(5,5,5,5);
        a2.weightx = 0.5;
        a2.weighty = 0.5;
        a2.gridx = 0;
        a2.gridy = 0;
        mainGameScene.add(addConsole(),a2);

        a2.fill = GridBagConstraints.BOTH;
        a2.anchor = GridBagConstraints.CENTER;
        a2.insets = new Insets(5, 5, 5, 5);
        a2.weightx = 8;
        a2.weighty = 0.5;
        a2.gridx = 1;
        a2.gridy = 0;
        mainGameScene.add(addMap(),a2);

        a2.fill = GridBagConstraints.BOTH;
        a2.anchor = GridBagConstraints.LINE_END;
        a2.insets = new Insets(5, 5, 5, 5);
        a2.weightx = 0.5;
        a2.weighty = 0.5;
        a2.gridx = 2;
        a2.gridy = 0;
        mainGameScene.add(addAction(),a2);

    }

    public JPanel addConsole(){

        DefaultCaret style;

        consolePanel.setPreferredSize(new Dimension(300,980));
        GridBagConstraints a3 = new GridBagConstraints();

        System.setOut(new PrintStream(new ChangeOutputStream(consoleUpdate)));
        consoleUpdate.setFocusable(false);
        consoleUpdate.setLineWrap(true);
        style = (DefaultCaret)consoleUpdate.getCaret();
        style.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        consoleMove.add(consoleUpdate);

        a3.fill = GridBagConstraints.BOTH;
        a3.insets = new Insets(5, 5, 5, 5);
        a3.weightx = 0.5;
        a3.weighty = 14;
        a3.gridx = 0;
        a3.gridy = 0;
        consolePanel.add(consoleMove,a3);
        return consolePanel;

    }

    public JPanel addMap(){

        mapPanel.setLayout(new GridLayout(1,1,5,5));
        mapScroll.setPreferredSize(new Dimension(1080,980));

        mapPanel.add(mapScroll);
        return mapPanel;
    }

    public JPanel addAction(){

        actionPanel.setPreferredSize(new Dimension(200,980));
        actionPanel.setLayout(actionLayout);

        chooseCountry = new JLabel("Choose a country");
        adjacentCountry = new JLabel("Adjacent Countries");

        attackButton = new JButton("Attack!!!");
        passTurn = new JButton("Meh, Pass");

        attackButton.setActionCommand("Attack");
        passTurn.setActionCommand("Pass");

        selectedCountries.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        selectedCountries.setLayoutOrientation(JList.VERTICAL_WRAP);
        selectedCountries.setVisibleRowCount(42);

        connectedCountries.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        connectedCountries.setLayoutOrientation(JList.VERTICAL_WRAP);
        connectedCountries.setVisibleRowCount(10);

        selectedCountryScrollPane.add(selectedCountries);
        connectedCountryScrollPane.add(connectedCountries);

        GridBagConstraints a4 = new GridBagConstraints();

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 4;
        actionPanel.add(chooseCountry, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 10;
        a4.gridx = 0;
        a4.gridy = 5;
        actionPanel.add(selectedCountryScrollPane, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 7;
        actionPanel.add(adjacentCountry, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 10;
        a4.gridx = 0;
        a4.gridy = 8;
        actionPanel.add(connectedCountryScrollPane, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 9;
        actionPanel.add(attackButton, a4);

        a4.fill = GridBagConstraints.BOTH;
        a4.insets = new Insets(5, 5, 5, 5);
        a4.weightx = 0.5;
        a4.weighty = 0.5;
        a4.gridx = 0;
        a4.gridy = 11;
        actionPanel.add(passTurn, a4);

        return actionPanel;
    }

    public void setInfoPanel(){

        infoPanel.setPreferredSize(new Dimension(320,980));

        //repeat the code below for all continent panels : @Hassan
        northAmericaPanel.setPreferredSize(new Dimension(320,100));
        northAmericaPanel.setLayout(new GridLayout(5,2,5,5));

        /**
         * @Hassan
        for (i = 0; i < map.getCountries("North America").size(); i++) {
            Go through each country in the continent and create a jLabel for each country
         then add that label to the each continent JPanel. Repeat this for all continent Panels in this class
        }
        **/

        GridBagConstraints a5 = new GridBagConstraints();

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 0;
        infoPanel.add(new JLabel("North America"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 1;
        infoPanel.add(northAmericaPanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 2;
        infoPanel.add(new JLabel("South America"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 3;
        infoPanel.add(southAmericaPanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 4;
        infoPanel.add(new JLabel("Europe"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 5;
        infoPanel.add(europePanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 6;
        infoPanel.add(new JLabel("Africa"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 7;
        infoPanel.add(africaPanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 8;
        infoPanel.add(new JLabel("Asia"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 9;
        infoPanel.add(asiaPanel, a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 10;
        infoPanel.add(new JLabel("Australia"), a5);

        a5.fill = GridBagConstraints.BOTH;
        a5.insets = new Insets(5, 5, 5, 5);
        a5.weightx = 0.5;
        a5.weighty = 0.5;
        a5.gridx = 0;
        a5.gridy = 11;
        infoPanel.add(australiaPanel, a5);
    }

    @Override
    public void updateCountries(PlayerEvent c) {
        ownCountries.addAll(c.getPlayerCountries());
    }

    @Override
    public void updateAdjacentCountries(PlayerEvent a, String countryName) {
        adjacentCountries.addAll(a.getAdjacentCountries(countryName));
    }
}
