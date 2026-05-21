
import ContributorContentAlbums from "@/components/contributor/editPhotoAlbum/editPhotoAlbum";

import styles from "./page.module.css"

export default function ContributorPhotoAlbum() {
    return (
        <main className= {styles.page}>
            <div>
                <ContributorContentAlbums/>
            </div>
        </main>
    );
}