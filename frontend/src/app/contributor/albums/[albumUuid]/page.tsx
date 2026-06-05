import ContributorContentPage from "@/components/contributors/edit-view-photoalbum/ContributorAlbumPage";
import ContributorAlbumsMenu from "@/components/contributors/contentMenu/contributorsContentMenu";

import styles from "./page.module.css"

export default function ContributorPhotoAlbumPage() {

    
    return (
        <>
        <ContributorAlbumsMenu />
        <ContributorContentPage />
        </>
    );

}