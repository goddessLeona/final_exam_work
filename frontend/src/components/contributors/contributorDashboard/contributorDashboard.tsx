"use client";

import ContributorAlbumsMenu from "../contentMenu/contributorsContentMenu";
import UploadPhotosForm from "../uploadAlbumForm/uploadPhotosForm";

export default function ContributorDashboard() {
  return (
    <>
      <ContributorAlbumsMenu />
      <UploadPhotosForm />
    </>
  );
}