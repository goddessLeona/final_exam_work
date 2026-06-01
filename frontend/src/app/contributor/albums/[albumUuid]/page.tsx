
import ContributorContentPage from "@/components/contributors/edit-view-photoalbum/ContributorAlbumPage";

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