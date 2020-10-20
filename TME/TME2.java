package TME;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class TME2 {

	
	public static byte[] concat_hash(byte[] a,byte[] b) {
		byte[] hash = new byte[a.length + b.length];
        System.arraycopy(a, 0, hash, 0, a.length);
        System.arraycopy(b, 0, hash, a.length, b.length);
        MessageDigest digest;
        byte[] res = null;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			res = digest.digest(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        
        return res;
	}
	
	private static ArrayList<MerkleTree> tranformMerkle(ArrayList<byte[]> l) {
		ArrayList<MerkleTree> res = new ArrayList<MerkleTree>();
		MessageDigest digest;
		byte[] tmp = null;
		for (byte[] b :l ) {
			try {
				digest = MessageDigest.getInstance("SHA-256");
				tmp = digest.digest(b);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			res.add(new MerkleTree(tmp, null,null));
		}
		return res;
	}
	
	public static MerkleTree create_merkle_tree(ArrayList<byte[]> l) {
		ArrayList<MerkleTree> new_l = tranformMerkle(l);
		ArrayList<MerkleTree> temp = new ArrayList<MerkleTree>();
		while (new_l.size()>1) {
					
			MerkleTree gauche = new_l.get(0);
			MerkleTree droite = new_l.get(1);
			
			gauche.setPos(0);
			droite.setPos(1);
			
			byte[] tmp = concat_hash(gauche.getValue(),droite.getValue());
			MerkleTree pere = new MerkleTree(tmp, gauche, droite);
			gauche.setPere(pere);
			droite.setPere(pere);
			temp.add(pere);
			new_l.remove(gauche);
			new_l.remove(droite);
			
			if(new_l.size()==0)
				new_l=temp;
		}
		return new_l.get(0);
	}
	
	public static MerkleTree witness(MerkleTree tree,MerkleTree leaf) {
		if (leaf.getPere()==null)
			return leaf;
		
		MerkleTree pere = leaf.getPere();
		
		while(pere.getPere()!=null) {
			MerkleTree tmp = null;
			if(pere.getPos()==0) {
				tmp = new MerkleTree(pere.getPere().getDroite().getValue(), null, null);
				tmp.setPere(pere.getPere());
				tmp.setPos(1);
				pere.getPere().setDroite(tmp);
			}
			else {
				tmp = new MerkleTree(pere.getPere().getGauche().getValue(), null, null);
				tmp.setPere(pere.getPere());
				tmp.setPos(0);
				pere.getPere().setGauche(new MerkleTree(pere.getPere().getGauche().getValue(), null, null));
			}
			pere=pere.getPere();
			
		}
		return pere;
	}
	
	public static boolean verify(MerkleTree tree, MerkleTree temoin) {
		while (temoin.getGauche()!=null && temoin.getDroite()!=null) {
			
		}
		
		return true;
	}
	
}
