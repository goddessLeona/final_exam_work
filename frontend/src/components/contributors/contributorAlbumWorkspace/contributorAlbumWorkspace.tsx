"use client";

import { useState } from "react";
import ContributorAlbumsMenu from "../contentMenu/contributorsContentMenu";
import ContributorContentPage from "../edit-view-photoalbum/ContributorAlbumPage";
import { ContentStatus } from "@/types/content-status";

export default function AlbumWorkspace() {

    const [selectedAlbumUuid, setSelectedAlbumUuid] = useState<string | null>(null);
    const [status, setStatus] = useState<ContentStatus | null>(null);

    return (
        <>
            <ContributorAlbumsMenu
                onAlbumSelect={(uuid) => setSelectedAlbumUuid(uuid)}
                onStatusChange={(newStatus) => {
                    setStatus(newStatus);
                    setSelectedAlbumUuid(null); 
                }}
            />

            {selectedAlbumUuid ? (
                <ContributorContentPage albumUuid={selectedAlbumUuid} />
            ) : (
                <p>Select an album</p>
            )}
        </>
    );
}