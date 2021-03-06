/*
 * Copyright (c) 2012-2015 Oleg Dolya
 *
 * Copyright (c) 2014 Edu Garcia
 *
 * Copyright (c) 2021 Yi An
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.watabou.noosa;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

import com.watabou.glscripts.Script;
import com.watabou.glwrap.Attribute;
import com.watabou.glwrap.Quad;
import com.watabou.glwrap.Uniform;

public class NoosaScript extends Script {
	
	public Uniform uCamera;
	public Uniform uModel;
	public Uniform uTex;
	public Uniform uColorM;
	public Uniform uColorA;
	public Attribute aXY;
	public Attribute aUV;
	
	private Camera lastCamera;
	
	public NoosaScript() {
		
		super();
		compile( shader() );
		
		uCamera	= uniform( "uCamera" );
		uModel	= uniform( "uModel" );
		uTex	= uniform( "uTex" );
		uColorM	= uniform( "uColorM" );
		uColorA	= uniform( "uColorA" );
		aXY		= attribute( "aXYZW" );
		aUV		= attribute( "aUV" );
		
	}
	
	@Override
	public void use() {
		
		super.use();
		
		aXY.enable();
		aUV.enable();
		
	}

	public void drawElements( FloatBuffer vertices, ShortBuffer indices, int size ) {

		((Buffer)vertices).position( 0 );
		aXY.vertexPointer( 2, 4, vertices );

		((Buffer)vertices).position( 2 );
		aUV.vertexPointer( 2, 4, vertices );
		
		Gdx.gl.glDrawElements( GL20.GL_TRIANGLES, size, GL20.GL_UNSIGNED_SHORT, indices );
		
	}
	
	public void drawQuad( FloatBuffer vertices ) {

		((Buffer)vertices).position( 0 );
		aXY.vertexPointer( 2, 4, vertices );

		((Buffer)vertices).position( 2 );
		aUV.vertexPointer( 2, 4, vertices );

		Gdx.gl.glDrawElements( GL20.GL_TRIANGLES, Quad.SIZE, GL20.GL_UNSIGNED_SHORT, Quad.INDICES_1 );
		
	}
	
	public void drawQuadSet( FloatBuffer vertices, int size ) {
		
		if (size == 0) {
			return;
		}

		((Buffer)vertices).position( 0 );
		aXY.vertexPointer( 2, 4, vertices );

		((Buffer)vertices).position( 2 );
		aUV.vertexPointer( 2, 4, vertices );

		Gdx.gl.glDrawElements(
			GL20.GL_TRIANGLES,
			Quad.SIZE * size,
			GL20.GL_UNSIGNED_SHORT,
			Quad.getIndices( size ) );
		
	}
	
	public void lighting( float rm, float gm, float bm, float am, float ra, float ga, float ba, float aa ) {
		uColorM.value4f( rm, gm, bm, am );
		uColorA.value4f( ra, ga, ba, aa );
	}
	
	public void resetCamera() {
		lastCamera = null;
	}
	
	public void camera( Camera camera ) {
		if (camera == null) {
			camera = Camera.main;
		}
		if (camera != lastCamera) {
			lastCamera = camera;
			uCamera.valueM4( camera.matrix );
			
			Gdx.gl.glScissor(
				camera.x, 
				Game.height - camera.screenHeight - camera.y, 
				camera.screenWidth, 
				camera.screenHeight );
		}
	}
	
	public static NoosaScript get() {
		return Script.use( NoosaScript.class );
	}
	
	
	protected String shader() {
		return SHADER;
	}
	
	private static final String SHADER =
		
		"uniform mat4 uCamera;" +
		"uniform mat4 uModel;" +
		"attribute vec4 aXYZW;" +
		"attribute vec2 aUV;" +
		"varying vec2 vUV;" +
		"void main() {" +
		"  gl_Position = uCamera * uModel * aXYZW;" +
		"  vUV = aUV;" +
		"}" +
		
		"//\n" +
		"#ifdef GL_ES\n" +
		"precision mediump float;\n" +
		"#endif\n" +
		"varying vec2 vUV;" +
		"uniform sampler2D uTex;" +
		"uniform vec4 uColorM;" +
		"uniform vec4 uColorA;" +
		"void main() {" +
		"  gl_FragColor = texture2D( uTex, vUV ) * uColorM + uColorA;" +
		"}";
}
