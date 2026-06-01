
import Welcome from "@/components/contributors/wellcomeMessage/welcome-message";
import ContributorAgrementForm from "@/components/forms/contributorAgrementForm";
import styles from "@/app/contributor/page.module.css"

export default function ContributorPage() {
    return (
        <main className= {styles.page}>
            <Welcome />
            <ContributorAgrementForm />
        </main>
    )
    
}






