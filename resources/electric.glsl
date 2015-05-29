uniform float iOvertoneVolume;

void main()
{
  float freqs[4];
  freqs[0] = texture2D(iChannel0, vec2( 0.01, 0.25 ) ).x;
  freqs[1] = texture2D(iChannel0, vec2( 0.07, 0.25 ) ).x;
  freqs[2] = texture2D(iChannel0, vec2( 0.15, 0.25 ) ).x;
  freqs[3] = texture2D(iChannel0, vec2( 0.30, 0.25 ) ).x;

  vec2 uv = gl_FragCoord.xy / iResolution.xy;

  vec2 p = uv*2.0 - 1.0;
  p *= 35.0;

  vec2 sfunc = vec2(p.x, p.y + freqs[1]*100*sin(uv.x*10.0-iGlobalTime*5.0 + cos(iGlobalTime*7.0) ) + freqs[1]*200.0*cos(uv.x*25.0+iGlobalTime*4.0));
  // vec2 sfunc = vec2(p.x, p.y + 55*sin(uv.x*10.0-iGlobalTime*1.0 + cos(iGlobalTime*7.0) ) + 2.0*cos(uv.x*25.0+iGlobalTime*4.0));

  sfunc.y *= uv.x*2.0+0.05;
  sfunc.y /= uv.x+freqs[2];
  sfunc.y *= 2.0 - uv.x*2.0+0.05;
  sfunc.y /= freqs[0] + 0.001;

  vec3 c = vec3(abs(sfunc.y));
  c = pow(c, vec3(-0.5));
  c *= vec3(0.16, 0.63, 0.60);

  gl_FragColor = vec4(c,1.0);
}
