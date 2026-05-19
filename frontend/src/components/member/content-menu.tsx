"use client";

import { useEffect, useState } from "react";
import styles from "./content-menu.module.css"

function MemberMenuContent(){

    return (
        <div className={styles.container}>
            <div className={styles.menu}>

                <button
                    type="button"
                    className={styles.btn}
                >
                    PHOTO
                </button>

                <button
                    type="button"
                    className={styles.btn}
                >
                    VIDEO
                </button>

                <button
                    type="button"
                    className={styles.btn}
                >
                    LATEST
                </button>

                <button
                    type="button"
                    className={styles.btn}
                >
                    NEWS
                </button>

                <button
                    type="button"
                    className={styles.btn}
                >
                    CONTRIBUTORS
                </button>

            </div>
        </div>
    )

}
export default MemberMenuContent