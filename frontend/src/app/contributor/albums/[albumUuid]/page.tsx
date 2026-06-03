
import ContributorContentPage from "@/components/contributors/edit-view-photoalbum/ContributorAlbumPage";
import ContributorAlbums from "@/components/contributors/contentMenu/contributorsContentMenu";

import styles from "./page.module.css"

export default function ContributorPhotoAlbum() {
    return (
        <main className= {styles.page}>
            <div>
                <ContributorAlbums/>
                <ContributorContentPage/>
            </div>
        </main>
    );
}