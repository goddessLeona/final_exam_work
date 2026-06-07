import MemberContentAlbums from "@/components/member/member-get-content-albums/albums";
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