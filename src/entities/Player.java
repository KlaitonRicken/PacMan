package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{
	
	public boolean right,up,left,down;
	public BufferedImage sprite_right, sprite_left, sprite_up, sprite_down;
	public BufferedImage sprite_right_eat, sprite_left_eat, sprite_up_eat, sprite_down_eat;
	
	public int atlApplee;
	public int lastDir = 1;
	public boolean moved;
	
	public BufferedImage [] spriteLife;
	public int life = 4;
	public int hit;
	public int maxHit = 30;
	
	int cont;
	int maxFrames = 6;
	int frames;

	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		sprite_left = Game.spritesheet.getSprite(48, 0, 16, 16);
		sprite_right = Game.spritesheet.getSprite(32, 0, 16, 16);
		sprite_up = Game.spritesheet.getSprite(64, 0, 16, 16);
		sprite_down = Game.spritesheet.getSprite(80, 0, 16, 16);
		
		sprite_left_eat = Game.spritesheet.getSprite(48, 16, 16, 16);
		sprite_right_eat = Game.spritesheet.getSprite(32, 16, 16, 16);
		sprite_up_eat = Game.spritesheet.getSprite(64, 16, 16, 16);
		sprite_down_eat = Game.spritesheet.getSprite(80, 16, 16, 16);
		
		spriteLife = new BufferedImage [4];
		
		for(int i = 0; i<life; i++) {
			spriteLife[i] = Game.spritesheet.getSprite(16, 16, 16, 16);
		}
		
		depth = 1;
		
	}
	
	public void checkCollidingApple() {
		
		for(int i=0; i<Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Apple) {
				if(Entity.isColidding(this, current)){
					Game.entities.remove(i);
					atlApplee++;
					return;
				}
			}
		}
	}
	
	public void checkCollidingEnemy() {
	
		for(int i=0; i<Game.entities.size(); i++) {
			Entity current = Game.entities.get(i);
			if(current instanceof Enemy) {
				Enemy en = (Enemy) current;
				if(Entity.isColidding(this, current) && !en.ghostMode ){
					hit++;
					return;
				}
			}
		}
	}
	
	public void tick(){
		
		checkCollidingApple();
		checkCollidingEnemy();
		
		if(hit == maxHit) {
			hit = 0;
			life--;
		}
		
		if(Game.contApple == Game.player.atlApplee) {
			System.out.println("Pr�ximo level");
			World.restartGame();
			return;
		}
		
		cont++;
		if(cont == maxFrames) {
			cont = 0;
			frames++; 
		}
		
		if(frames == maxFrames)
			frames = 0;
		
		System.out.println(frames);
		
		if(right && World.isFree((int)(x+speed),this.getY())) {
			x+=speed;
			lastDir = 1;
		}
		else if(left && World.isFree((int)(x-speed),this.getY())) {
			x-=speed;
			lastDir = 2;
		}
		if(up && World.isFree(this.getX(),(int)(y-speed))){
			y-=speed;
			lastDir = 3;
		}
		else if(down && World.isFree(this.getX(),(int)(y+speed))){
			y+=speed;
			lastDir = 4;
		}
	}
	
	public void render(Graphics g){
		
		if(!moved) {
			if(lastDir == 1) {
				g.drawImage(sprite_right,this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(lastDir == 2){
				g.drawImage(sprite_left,this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(lastDir == 3){
				g.drawImage(sprite_up,this.getX() - Camera.x,this.getY() - Camera.y,null);
			}else if(lastDir == 4){
				g.drawImage(sprite_down,this.getX() - Camera.x,this.getY() - Camera.y,null);
			}
		}else {
		
			if(right) {
				if(frames < maxFrames/2)
					g.drawImage(sprite_right_eat,this.getX() - Camera.x,this.getY() - Camera.y,null);
				else
					g.drawImage(sprite_right,this.getX() - Camera.x,this.getY() - Camera.y,null);
				
			}else if(left){
				if(frames < maxFrames/2)
					g.drawImage(sprite_left_eat,this.getX() - Camera.x,this.getY() - Camera.y,null);
				else
					g.drawImage(sprite_left,this.getX() - Camera.x,this.getY() - Camera.y,null);
					
			}else if(up){
				if(frames < maxFrames/2)
					g.drawImage(sprite_up_eat,this.getX() - Camera.x,this.getY() - Camera.y,null);
				else
					g.drawImage(sprite_up,this.getX() - Camera.x,this.getY() - Camera.y,null);
				
			}else if(down){
				if(frames < maxFrames/2)
					g.drawImage(sprite_down_eat,this.getX() - Camera.x,this.getY() - Camera.y,null);
				else
					g.drawImage(sprite_down,this.getX() - Camera.x,this.getY() - Camera.y,null);
				
			}
		}
				
	}
}
