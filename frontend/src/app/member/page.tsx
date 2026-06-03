
import MemberMenuContent from "@/components/member/member-menu-content/content-menu";
import styles from "./page.module.css"

export default function MemberPage() {
    return (
        <main className= {styles.page}>
            <div>
                <MemberMenuContent/>
            </div>
        </main>
    );
}