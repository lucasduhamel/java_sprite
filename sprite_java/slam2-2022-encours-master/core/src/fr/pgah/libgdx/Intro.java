package fr.pgah.libgdx;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Intro extends ApplicationAdapter {

  final int NB_SPRITES = 5;
  SpriteBatch batch;
  int longueurFenetre;
  int hauteurFenetre;
  // Sprite[] sprites;
  ArrayList<Sprite> sprites;
  // Joueur joueur;
  boolean gameOver;
  Texture gameOverTexture;
  int nombre_de_sprite_actif;
  int nombre_de_tire_utilise;

  @Override
  public void create() {
    batch = new SpriteBatch();
    longueurFenetre = Gdx.graphics.getWidth();
    hauteurFenetre = Gdx.graphics.getHeight();
    nombre_de_tire_utilise = 0;
    gameOver = false;
    gameOverTexture = new Texture("game_over.png");
    nombre_tire_utilise();
    initialisationSprites();
    // initialiserJoueur();
  }

  private void initialisationSprites() {
    // sprites = new Sprite[NB_SPRITES];
    sprites = new ArrayList<>();
    for (int i = 0; i < NB_SPRITES; i++) {
      sprites.add(new Sprite("chien.png"));
    }
  }

  //private void initialiserJoueur() {
  //  joueur = new Joueur();
  //}

  @Override
  public void render() {
    // gameOver est mis à TRUE dès qu'un "hit" est repéré
    if (!gameOver) {
      reinitialiserArrierePlan();
      majEtatProtagonistes();
      majEtatJeu();
      dessiner();
    }
  }


  private void reinitialiserArrierePlan() {
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
  }

  private void majEtatProtagonistes() {
    // Sprites
    for (Sprite sprite : sprites) {
      sprite.majEtat();
    }

    // Joueur
    // joueur.majEtat();
  }

  private void majEtatJeu() {
    // On vérifie si le jeu continue ou pas
    //if (joueur.estEnCollisionAvec(sprites)) {
     //  gameOver = true;
    //}
    if(Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) {

      // ajoute +1 à la variable nombre_de_tire_utilise (int)
      nombre_de_tire_utilise += 1;
      // appel à la méthode d'affichage de tire utilisé
      nombre_tire_utilise();
      gererTir();
    }
  
    // regarde si y'en a encore de sprite

    //if (sprites.size() == 0) {
      if (sprites.isEmpty()){
        gameOver = true;
      }
    }


  private void gererTir() {
    int tirX = Gdx.input.getX();
    int tirY = Gdx.input.getY();
    
    ArrayList<Sprite> spritesTouches = new ArrayList<>();

    for (Sprite sprite : sprites) {
      if (sprite.seTrouveSur(tirX, tirY)) {
        // sprites.remove(sprite);

        // retenir le sprite 
        spritesTouches.add(sprite);
        //System.out.println(sprites.size());
        //break; // sortir immédiatement de la boucle car on à trouver
      }
    }

    // effacer le sprite hit potentiel 
    // if (spriteTouche != null) {
    //   sprites.remove(spriteTouche);
    // }

    // effacer tous les sprites touchés
    // for (Sprite spriteTouche : spritesTouches) {
    //  sprites.remove(spriteTouche);
    //}

      sprites.removeAll(spritesTouches);
      System.out.println("nombre de sprite restant : " + sprites.size());
  }

  private void dessiner() {
    batch.begin();
    if (gameOver) {
      // cet affichage GAME OVER ne se fera qu'une fois
      // à la fin de la dernière frame au moment du "hit"
      // puisqu'ensuite le render ne fera plus rien
      batch.draw(gameOverTexture, 100, 100);
    } else {
      // Affichage "normal", jeu en cours
      for (Sprite sprite : sprites) {
        sprite.dessiner(batch);
      }
      // joueur.dessiner(batch);
    }
    batch.end();
  }



  private void nombre_tire_utilise() {
    System.out.println("--------------------------");
    System.out.println("Nombre de tire utilisé : " + nombre_de_tire_utilise);
  }
}
