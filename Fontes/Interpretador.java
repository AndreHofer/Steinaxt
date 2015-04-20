/*
Nomes: Leonardo Bianchini e João C. Becker 
Emails: joaoc.becker@hotmail.com e leonardobianchini7@gmail.com

	Essa classe contém o interpretador
*/

class Interpretador {
	private String linhas[];
	public Variavel vars [];
	private String linha;
	public int numvar;//vai conter o numero de variaveis alocadas
	
	public Interpretador(String l[]){
		this.linhas = l;
		vars = new Variavel [1000];
		numvar = 0;
	}
	
	public void Test (){
		vars [numvar]= new Int ("number",7);
		numvar++;
		vars [numvar]= new Real ("pi",3.1415);
		numvar++;
		vars [numvar]= new Text ("texto","James_hetfield");
		numvar++;
		
	}
	
	public void interpreta(int i) {//parametro int para poder fazer recurção na interpretação, i = linha
		for(; i < this.linhas.length; i++) {
			if(this.linhas[i] != null) {
				linha = linhas[i].trim();//faz cópia e retira tabulação, espaços no inicio e final
				
				
				if (linha.startsWith("int ")){//declaração de inteiro
					
					
					System.out.println ("Declaração de inteiro na linha "+ (i+1));
					String varsline = new String (linha.substring (4));
					if (!(varsline.isEmpty())){
						if (varsline.contains(",")){//declara mais que um inteiro
							String[] varofline = varsline.split(",");
							
							for (int k=0; k<varofline.length; k++){
								if (varofline[k].contains("=")){
									
									String[] atributo = varofline[k].split("=");
									
									if (atributo.length != 2){
										System.out.println ("Erro na linha "+(i+1)+" declaração não reconhecida");
										System.out.println ("Erro perto de "+ varofline[k]);
										return ;
									}
									varofline[k]=atributo[0].trim();
									int a= 0;
									try{
										a= Integer.parseInt(atributo[1].trim());
									}catch (NumberFormatException e) {
										System.out.println("Erro na linha "+(i+1)+" declaração não reconhecida para catch numb");
										System.out.println ("Erro perto de "+ varofline[k]);
										return ;
									}
									if (validname(varofline[k], i+1)){
										vars [numvar]= new Int (varofline[k],a);
										numvar++;
									}else{
										return;
									}
								}
								else{//declara sem atribuição
									varofline[k]=varofline[k].trim();
									if (validname(varofline[k], i+1)){
										vars [numvar]= new Int (varofline[k],0);
										numvar++;
									}else{
										return;
									}
								}
								System.out.println (varofline[k]);
							}
						}else{
							if (varsline.contains("=")){//uma variavel com atrbuição
								String[] atributo = varsline.split("=");
								if (atributo.length != 2){
									System.out.println ("Erro na linha "+(i+1)+" declaração não reconhecida");
									System.out.println ("Erro perto de "+ varsline);
									return ;
								}
								varsline=atributo[0].trim();
								int a= 0;
								try{
									a= Integer.parseInt(atributo[1].trim());
								}catch (NumberFormatException e) {
									System.out.println("Erro na linha "+(i+1)+" declaração não reconhecida para catch numb");
									System.out.println ("Erro perto de "+ varsline);
									return ;
								}
								if (validname(varsline, i+1)){
									vars [numvar]= new Int (varsline,a);
									numvar++;
								}else{
									return;
								}
								
								
							}else{//uma variavel sem atribuição
								varsline=varsline.trim();
								if (validname(varsline, i+1)){
									vars [numvar]= new Int (varsline,0);
									numvar++;
								}else{
									return;
								}
								
							}
								
						}
						
					}else{
						System.out.println ("Erro na linha "+(i+1)+" declaração não reconhecida");
						return;
						
					}
					
				}
				else if (linha.startsWith ("real ")){
					System.out.println ("Declaração de real na linha"+ (i+1));
					
				}
				
				else if (linha.startsWith ("text ")){
					System.out.println ("Declaração de string na linha"+ (i+1));
					
				}
				
				
				else if (linha.startsWith ("loop ")){
					System.out.println ("Inicio de laço na linha "+ (i+1));
					
				}
				
				else if (linha.startsWith ("if ")){
					System.out.println ("If reconhecido na linha "+ (i+1));
					
				}
				
				else if (linha.startsWith ("show")){
					System.out.println("Comando de saida reconhecido na linha "+ (i+1));
					
				}
				
				else if (linha.contains ("=")){
					System.out.println ("Atribuição reconhecida na linha "+(i+1));
					
				}
				
//				System.out.println("Linha " + (i + 1) + ":" + linha);
				
				
				
				
				
				
				
				
				
			}
		}
		printVars();
	}
	
	public void printVars(){
		
		
		for (int i = 0; i<numvar; i++){
			if (vars [i] == null){
				System.out.println ("não existe variavel na posição "+(i));
			}
			else if (vars [i] instanceof Int){
				System.out.println ("Pos:"+i+" Tipo:int "+"Nome:"+(vars[i].getNome())+" Valor:"+ ((Int)vars[i]).getValor());
				
			}
			
			else if (vars [i] instanceof Real){
				System.out.println ("Pos:"+i+" Tipo:real "+"Nome:"+(vars[i].getNome())+" Valor:"+ ((Real)vars[i]).getValor());
				
			}
			
			else if (vars [i] instanceof Text){
				System.out.println ("Pos:"+i+" Tipo:text "+"Nome:"+(vars[i].getNome())+" Conteudo:"+ ((Text)vars[i]).getConteudo());
				
				
			}
			
			else{
				System.out.println ("Variavel da posição "+(i+1) +" não reconhecida");
				
			}
			
		}
		
	}
	
	public boolean validname(String name, int line){//verifica se string é um nome valido
		
		for (int i=0; i<numvar; i++){
			if (name.equals(vars[i].getNome())){
				System.out.println ("Erro na linha "+line+" já existe uma variavel com o nome "+name);
				return false;
			}
			
		}
		if (name.contains (" ")|| name.contains ("!")|| name.contains ("@")
		||  name.contains ("#")|| name.contains ("$")|| name.contains ("%")
		||  name.contains ("&")|| name.contains ("*")|| name.contains ("(")
		||  name.contains (")")|| name.contains ("-")|| name.contains ("+")
		||  name.contains ("=")|| name.contains (";")){
			System.out.println ("Erro na linha "+line+" variavel "+name+" obtem caracter especial");
			return false;
			
		}
		if ( name.charAt(0) >= '0' && name.charAt(0) <= '9'){
			System.out.println ("Erro na linha "+line+" variavel "+name+" começa com numero");
			return false;
			
		}
		
		if (name.equals("int") || name.equals("real") || name.equals("text") || 
		name.equals("loop") || name.equals("show") || name.equals("if")){
			System.out.println ("Erro na linha "+line+" variavel "+name+" com palavra reservada");
			return false;
		}
		
		
		return true;
	}
	
	
}
