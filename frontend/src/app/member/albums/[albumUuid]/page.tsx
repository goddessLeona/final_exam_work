import MemberContentAlbums from "@/components/member/albums";
import styles from "./page.module.css"

export default function MemberPhotoAlbum() {
    return (
        <main className= {styles.page}>
            <div>
                <MemberContentAlbums/>
            </div>
        </main>
    );
}