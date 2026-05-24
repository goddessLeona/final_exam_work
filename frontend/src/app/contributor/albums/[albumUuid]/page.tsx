
import ContributorContentPage from "@/components/contributor/edit-view-photoalbum/ContributorAlbumPage";

import styles from "./page.module.css"

export default function ContributorPhotoAlbum() {
    return (
        <main className= {styles.page}>
            <div>
                <ContributorContentPage/>
            </div>
        </main>
    );
}